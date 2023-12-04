package com.project.pescueshop.service;

import com.project.pescueshop.config.PaymentConfig;
import com.project.pescueshop.model.dto.InvoiceItemDTO;
import com.project.pescueshop.model.dto.PaymentInfoDTO;
import com.project.pescueshop.model.dto.SingleItemCheckOutInfoDTO;
import com.project.pescueshop.model.entity.*;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.repository.dao.CartDAO;
import com.project.pescueshop.repository.dao.PaymentDAO;
import com.project.pescueshop.util.Util;
import com.project.pescueshop.util.constant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentDAO paymentDAO;
    private final CartDAO cartDAO;
    private final VarietyService varietyService;
    private final UserService userService;

    public String createPaymentLink(String content, String returnUrl, long value) throws UnsupportedEncodingException {

        long amount = value * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", PaymentConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_TxnRef", content);
        vnp_Params.put("vnp_OrderInfo", content);
        vnp_Params.put("vnp_OrderType", PaymentConfig.orderType);
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        ZonedDateTime createDate = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        // Format ZonedDateTime using DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String vnp_CreateDate = createDate.format(formatter);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        ZonedDateTime expireDate = createDate.plusHours(2);
        String vnp_ExpireDate = expireDate.format(formatter);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

    public String userCartCheckout(User user, PaymentInfoDTO dto) throws UnsupportedEncodingException {
        EnumPaymentType paymentType = EnumPaymentType.getByValue(dto.getPaymentType());
        Address address = dto.getAddress();

        Invoice invoice = new Invoice();
        invoice.setPaymentType(paymentType.getValue());
        invoice.setUserId(user.getUserId());
        invoice.setCityName(address.getCityName());
        invoice.setDistrictName(address.getDistrictName());
        invoice.setWardName(address.getWardName());
        invoice.setStreetName(address.getStreetName());
        invoice.setStatus(EnumInvoiceStatus.PENDING.getValue());
        invoice.setPhoneNumber(dto.getPhoneNumber());
        invoice.setCreatedDate(Util.getCurrentDate());
        invoice.setVoucher(dto.getVoucher());

        long invoiceValue = cartDAO.sumValueOfAllSelectedProductInCart(user.getUserId());
        invoice.setTotalPrice(invoiceValue);
        invoice.setFinalPrice(invoiceValue);

        if (invoice.getVoucher() != null){
            Voucher voucher = invoice.getVoucher();
            EnumVoucherType voucherType = EnumVoucherType.getByValue(voucher.getType());
            long discountAmount = 0;

            if (voucherType == EnumVoucherType.PERCENTAGE){
                discountAmount = (voucher.getValue() * invoice.getTotalPrice()) / 100L;
            }
            else {
                discountAmount = voucher.getValue();
            }

            discountAmount = Math.min(discountAmount, voucher.getMaxValue());
            invoice.setDiscountPrice(discountAmount);

            long finalPrice = Math.max(invoice.getTotalPrice() - discountAmount, 0);
            invoice.setFinalPrice(finalPrice);
        }

        paymentDAO.saveAndFlushInvoice(invoice);

        CompletableFuture.runAsync(() -> {
            addInvoiceItemsToInvoice(invoice);
            cartDAO.removeSelectedCartItem(user.getUserId());
        });

        if (paymentType == EnumPaymentType.CREDIT_CARD){
            return createPaymentLink("Invoice ID: " + invoice.getInvoiceId(), dto.getReturnUrl(), invoice.getFinalPrice());
        }
        invoice.setStatus(EnumInvoiceStatus.COMPLETED.getValue());
        paymentDAO.saveAndFlushInvoice(invoice);
        return  "";
    }
    public String singleItemCheckOut(User user, SingleItemCheckOutInfoDTO info) throws UnsupportedEncodingException {
        PaymentInfoDTO dto = info.getPaymentInfoDTO();
        EnumPaymentType paymentType = EnumPaymentType.getByValue(dto.getPaymentType());
        Address address = dto.getAddress();

        Invoice invoice = new Invoice();
        invoice.setPaymentType(paymentType.getValue());
        invoice.setUserId(user.getUserId());
        invoice.setCityName(address.getCityName());
        invoice.setDistrictName(address.getDistrictName());
        invoice.setWardName(address.getWardName());
        invoice.setStreetName(address.getStreetName());
        invoice.setStatus(EnumInvoiceStatus.PENDING.getValue());
        invoice.setPhoneNumber(dto.getPhoneNumber());
        invoice.setCreatedDate(Util.getCurrentDate());
        invoice.setVoucher(dto.getVoucher());

        Variety variety = varietyService.findById(info.getVarietyId());
        long invoiceValue = variety.getPrice();
        invoice.setTotalPrice(invoiceValue);
        invoice.setFinalPrice(invoiceValue);

        if (invoice.getVoucher() != null){
            Voucher voucher = invoice.getVoucher();
            EnumVoucherType voucherType = EnumVoucherType.getByValue(voucher.getType());
            long discountAmount = 0;

            if (voucherType == EnumVoucherType.PERCENTAGE){
                discountAmount = (voucher.getValue() * invoice.getTotalPrice()) / 100L;
            }
            else {
                discountAmount = voucher.getValue();
            }

            discountAmount = Math.min(discountAmount, voucher.getMaxValue());
            invoice.setDiscountPrice(discountAmount);

            long finalPrice = Math.max(invoice.getTotalPrice() - discountAmount, 0);
            invoice.setFinalPrice(finalPrice);
        }

        paymentDAO.saveAndFlushInvoice(invoice);

        InvoiceItem item = InvoiceItem.builder()
                .variety(variety)
                .quantity(info.getQuantity())
                .invoiceId(invoice.getInvoiceId())
                .varietyId(variety.getVarietyId())
                .totalPrice(variety.getPrice() * info.getQuantity())
                .build();

        paymentDAO.saveAndFlushItem(item);

        if (paymentType == EnumPaymentType.CREDIT_CARD){
            return createPaymentLink("Invoice ID: " + invoice.getInvoiceId(), dto.getReturnUrl(), invoice.getFinalPrice());
        }
        invoice.setStatus(EnumInvoiceStatus.COMPLETED.getValue());
        paymentDAO.saveAndFlushInvoice(invoice);
        return "";
    }

    public void addInvoiceItemsToInvoice(Invoice invoice) {
        cartDAO.addInvoiceItemsToInvoice(invoice);
    }

    public List<InvoiceItemDTO> getInvoiceDetail(String invoiceId){
        return paymentDAO.getInvoiceDetail(invoiceId);
    }

    public String userCartCheckoutUnAuthenticate(PaymentInfoDTO paymentInfoDTO) throws FriendlyException, UnsupportedEncodingException {
        User user = userService.getAdminUser();
        return userCartCheckout(user, paymentInfoDTO);
    }

    public String singleItemCheckOutUnAuthenticate(SingleItemCheckOutInfoDTO singleItemCheckOutInfoDTO) throws FriendlyException, UnsupportedEncodingException {
        User user = userService.getAdminUser();
        return singleItemCheckOut(user, singleItemCheckOutInfoDTO);
    }
}

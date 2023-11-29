package com.project.pescueshop.service;

import com.project.pescueshop.config.PaymentConfig;
import com.project.pescueshop.model.dto.PaymentOutputDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.util.constant.EnumResponseCode;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PaymentService {

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

    public void userCartCheckout(User user){

    }
}

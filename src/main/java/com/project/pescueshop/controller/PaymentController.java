package com.project.pescueshop.controller;

import com.project.pescueshop.config.PaymentConfig;
import com.project.pescueshop.model.dto.PaymentInputDTO;
import com.project.pescueshop.model.dto.PaymentOutputDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.util.constant.EnumResponseCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin
@RequiredArgsConstructor
@Api
public class PaymentController {

    @PostMapping("/create-payment")
    public ResponseEntity<ResponseDTO<PaymentOutputDTO>> createPayment(@RequestBody PaymentInputDTO dto) throws UnsupportedEncodingException {
        long amount = 100000 * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", PaymentConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_TxnRef", dto.getInvoiceId());
        vnp_Params.put("vnp_OrderInfo", "Invoice number:" + dto.getInvoiceId());
        vnp_Params.put("vnp_OrderType", PaymentConfig.orderType);
        vnp_Params.put("vnp_ReturnUrl", dto.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Etc/GMT+7"));
        Calendar cld = Calendar.getInstance();
        cld.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());
        cld.setTimeZone(TimeZone.getTimeZone(zonedDateTime.getZone()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        System.out.println(cld.getTime());

        cld.add(Calendar.HOUR, 2);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        System.out.println(cld.getTime());

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
        PaymentOutputDTO outputDTO = PaymentOutputDTO.builder()
                .status("OK")
                .message("Succeed")
                .url(paymentUrl)
                .build();
        ResponseDTO<PaymentOutputDTO> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, outputDTO, "output");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/payment-info")
    public ResponseEntity<?> transactions(){

        return null;
    }
}

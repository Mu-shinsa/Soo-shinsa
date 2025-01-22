package com.Soo_Shinsa.utils;

import com.Soo_Shinsa.constant.TossPayMethod;
import com.Soo_Shinsa.constant.TossPayStatus;
import com.Soo_Shinsa.dto.payment.PaymentApproveRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentResponseDto;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class JsonUtils {

    // 객체를 JSON 문자열로 변환
    public static String toJson(Object obj) throws JSONException {
        if (obj instanceof PaymentRequestDto) {
            PaymentRequestDto dto = (PaymentRequestDto) obj;
            JSONObject json = new JSONObject();
            json.put("paymentKey", dto.getPaymentKey());
            json.put("orderId", dto.getOrderId());
            json.put("method", dto.getMethod());
            return json.toString();
        }
        throw new IllegalArgumentException("지원하지 않는 객체 타입입니다.");
    }

    // 객체를 JSON 문자열로 변환
    public static String toJson2(Object obj) throws JSONException {
        if (obj instanceof PaymentApproveRequestDto) {
            PaymentApproveRequestDto dto = (PaymentApproveRequestDto) obj;
            JSONObject json = new JSONObject();
            json.put("paymentKey", dto.getPaymentKey());
            json.put("orderId", dto.getOrderId());
            json.put("method", dto.getMethod());
            json.put("amount",dto.getAmount());
            return json.toString();
        }
        throw new IllegalArgumentException("지원하지 않는 객체 타입입니다.");
    }

    // JSON 문자열을 PaymentResponseDto로 변환
    public static PaymentResponseDto fromJson(String jsonString, Class<PaymentResponseDto> paymentResponseDtoClass) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        Long id = json.getLong("id");
        String paymentKey = json.getString("paymentKey");
        String methodString = json.getString("method");
        TossPayMethod method = TossPayMethod.valueOf(methodString);
        BigDecimal amount = BigDecimal.valueOf(json.getDouble("amount"));
        String statusString = json.getString("status");
        TossPayStatus status = TossPayStatus.valueOf(statusString);
        String userEmail = json.getString("userEmail");
        String orderId = json.getString("orderId");
        return new PaymentResponseDto(id, paymentKey, method, amount, status, userEmail,orderId);
    }
    // JSON 문자열을 PaymentResponseDto로 변환
    public static PaymentResponseDto fromJson2(String jsonString, Class<PaymentResponseDto> paymentResponseDtoClass) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        Long id = json.getLong("id");
        String paymentKey = json.getString("paymentKey");
        String methodString = json.getString("method");
        TossPayMethod method = TossPayMethod.valueOf(methodString);
        BigDecimal amount = BigDecimal.valueOf(json.getDouble("amount"));
        String statusString = json.getString("status");
        TossPayStatus status = TossPayStatus.valueOf(statusString);
        String userEmail = json.getString("userEmail");
        String orderId = json.getString("orderId");
        return new PaymentResponseDto(id, paymentKey, method, amount, status, userEmail,orderId);
    }
}
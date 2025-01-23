package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.TossPayMethod;
import com.Soo_Shinsa.constant.TossPayStatus;

import com.Soo_Shinsa.dto.payment.PaymentApproveRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentResponseDto;
import com.Soo_Shinsa.model.OrderItem;
import com.Soo_Shinsa.model.Orders;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.model.Payment;
import com.Soo_Shinsa.repository.OrdersRepository;
import com.Soo_Shinsa.repository.PaymentRepository;
import com.Soo_Shinsa.service.TossPaymentsService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class TossPaymentsServiceImpl implements TossPaymentsService {
    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${toss.secret-key}")
    private String secretKey;

    @Value("${toss.client-key}")
    private String clientKey;


    @Transactional
    public void approvePayment(User user, String paymentKey, String orderId, Long amount, Model model) throws JsonProcessingException {


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Orders find = ordersRepository.findByOrderId(orderId);

        Payment payment = new Payment(
                paymentKey,
                orderId,
                find.getTotalPrice(),
                TossPayStatus.READY,
                TossPayMethod.CARD,
                find,
                user);

        paymentRepository.save(payment);
        Map<String, String> payloadMap = new HashMap<>();



        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(find.getTotalPrice()));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

    }
}
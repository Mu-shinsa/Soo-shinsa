package com.Soo_Shinsa.order;

import com.Soo_Shinsa.constant.TossPayMethod;
import com.Soo_Shinsa.constant.TossPayStatus;

import com.Soo_Shinsa.dto.payment.PaymentRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentResponseDto;
import com.Soo_Shinsa.dto.payment.UserOrderDTO;


import com.Soo_Shinsa.order.model.Orders;

import com.Soo_Shinsa.order.model.Payment;


import com.Soo_Shinsa.utils.user.PaymentRepository;
import com.Soo_Shinsa.utils.user.UserRepository;
import com.Soo_Shinsa.utils.user.model.User;
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

import java.util.*;


@Service
@RequiredArgsConstructor
public class TossPaymentsServiceImpl implements TossPaymentsService {
    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${toss.secret-key}")
    private String secretKey;

    @Value("${toss.client-key}")
    private String clientKey;


    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto, User user) {
        // 주문 및 사용자 조회
        Orders order = ordersRepository.findById(requestDto.getOrder())
                .orElseThrow(() -> new IllegalArgumentException("오더가 없습니다"));


        // Payment 엔티티 생성
        Payment payment = new Payment(
                order.getOrderId(),
                order.getTotalPrice(),
                TossPayStatus.READY,
                TossPayMethod.CARD,
                order,
                user

        );

        Payment savedPayment = paymentRepository.save(payment);

        // 응답 DTO 생성 및 반환
        return PaymentResponseDto.toDto(savedPayment);
    }


    @Transactional
    public void approvePayment(String paymentKey, String orderId, Long amount, Model model) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);



        Payment findPayment = paymentRepository.findByOrderId(orderId);


        findPayment.update(TossPayStatus.DONE,paymentKey);


        paymentRepository.save(findPayment);

        Map<String, String> payloadMap = new HashMap<>();



        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);



    }

    @Transactional
    public UserOrderDTO findItem(Long userId, Long orderId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 오더입니다."));



        return new UserOrderDTO(user,order);


    }
}
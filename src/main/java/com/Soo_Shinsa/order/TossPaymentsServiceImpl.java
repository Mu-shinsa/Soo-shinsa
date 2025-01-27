package com.Soo_Shinsa.order;

import com.Soo_Shinsa.constant.TossPayMethod;
import com.Soo_Shinsa.constant.TossPayStatus;
import com.Soo_Shinsa.order.dto.PaymentRequestDto;
import com.Soo_Shinsa.order.dto.PaymentResponseDto;
import com.Soo_Shinsa.order.dto.UserOrderDTO;
import com.Soo_Shinsa.order.model.Orders;
import com.Soo_Shinsa.order.model.Payment;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


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



    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto, User user) {

        Orders order = ordersRepository.findById(requestDto.getOrder())
                .orElseThrow(() -> new IllegalArgumentException("오더가 없습니다"));



        Payment payment = new Payment(
                order.getOrderId(),
                order.getTotalPrice(),
                TossPayStatus.READY,
                TossPayMethod.CARD,
                order,
                user

        );

        Payment savedPayment = paymentRepository.save(payment);


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

    @Override
    public void cancelPayment(String paymentKey,String cancelReason) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);



        Payment findPayment = paymentRepository.findByPaymentKey(paymentKey);


        findPayment.update(TossPayStatus.CANCELED,paymentKey);


        paymentRepository.save(findPayment);

        Map<String, String> payloadMap = new HashMap<>();

        payloadMap.put("cancelReason", cancelReason);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        // paymentKey를 URL에 동적으로 추가
        String url = String.format("https://api.tosspayments.com/v1/payments/%s/cancel", paymentKey);

        // API 호출
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url, request, JsonNode.class);

    }



    @Transactional
    public UserOrderDTO findItem(Long userId, Long orderId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 오더입니다."));



        return new UserOrderDTO(user,order);


    }


}
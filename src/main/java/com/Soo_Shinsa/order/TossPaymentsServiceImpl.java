package com.Soo_Shinsa.order;

import com.Soo_Shinsa.constant.TossPayMethod;
import com.Soo_Shinsa.constant.TossPayStatus;
import com.Soo_Shinsa.order.dto.PayloadRequestDto;
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

import static com.Soo_Shinsa.constant.OrdersStatus.PAYMENTCOMPLETED;


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
        findPayment.update(TossPayStatus.DONE, paymentKey);
        paymentRepository.save(findPayment);

        PayloadRequestDto payload = new PayloadRequestDto(orderId, String.valueOf(amount));
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payload), headers);
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        Orders findOrder = ordersRepository.findByOrderId(orderId);
        findOrder.updateStatus(PAYMENTCOMPLETED);
        ordersRepository.save(findOrder);
    }

    @Transactional
    @Override
    public void cancelPayment(String paymentKey, String cancelReason) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Payment findPayment = paymentRepository.findByPaymentKey(paymentKey);

        findPayment.update(TossPayStatus.CANCELED, paymentKey);
        paymentRepository.save(findPayment);

        PayloadRequestDto payload = new PayloadRequestDto(cancelReason);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payload), headers);

        // API 호출
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel", request, JsonNode.class);
    }


    @Transactional
    public UserOrderDTO findItem(Long userId, Long orderId) {
        User user = userRepository.findByIdOrElseThrow(userId);
        Orders order = ordersRepository.findByIdOrElseThrow(orderId);
        return new UserOrderDTO(user, order);
    }
}
package com.Soo_Shinsa.controller;



import com.Soo_Shinsa.dto.payment.PaymentRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentResponseDto;
import com.Soo_Shinsa.dto.payment.UserOrderDTO;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.TossPaymentsService;

import com.Soo_Shinsa.utils.UserUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RequestMapping("/api")
@Controller
@RequiredArgsConstructor
public class TossPaymentsController {
    private final TossPaymentsService tossPaymentsService;
    private final UserRepository userRepository;

    @Value("${toss.secret-key}")
    private String secretKey;

    @Value("${toss.client-key}")
    private String clientKey;


    // 결제 생성
    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDto> createPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid
            @RequestBody PaymentRequestDto requestDto) {
        User user = UserUtils.getUser(userDetails);
        PaymentResponseDto responseDto = tossPaymentsService.createPayment(requestDto,user);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @RequestMapping("/success")
    public String approvePayment (
            @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount,

            Model model) throws JsonProcessingException {


        return "success";
    }

    @RequestMapping("/home/users/{userId}/orders/{orderId}")
    public String home(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            Model model){
        UserOrderDTO item = tossPaymentsService.findItem(userId, orderId);
        BigDecimal totalPrice = item.getOrder().getTotalPrice();
        String orderName = item.getOrder().getOrderId();
        String name = item.getUser().getName();


        System.out.println(totalPrice);
        System.out.println(orderName);
        System.out.println(name);


        model.addAttribute("tosspayments_key", clientKey);

        model.addAttribute("totalPrice", totalPrice != null ? totalPrice : 1000);

        model.addAttribute("orderName", orderName);

        model.addAttribute("name", name);

        return "home";
    }
    @RequestMapping("/test")
    public String test(
            Model model){
        model.addAttribute("tosspayments_key", clientKey);
        return "test";
    }
}

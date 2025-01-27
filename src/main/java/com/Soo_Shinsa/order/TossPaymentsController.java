package com.Soo_Shinsa.order;


import com.Soo_Shinsa.order.dto.PaymentRequestDto;
import com.Soo_Shinsa.order.dto.PaymentResponseDto;
import com.Soo_Shinsa.order.dto.UserOrderDTO;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.UserUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/api")
@Controller
@RequiredArgsConstructor
public class TossPaymentsController {
    private final TossPaymentsService tossPaymentsService;


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
        tossPaymentsService.approvePayment(paymentKey,orderId,amount,model);

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



        model.addAttribute("tosspayments_key", clientKey);

        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("orderName", orderName);

        model.addAttribute("name", name);

        return "home";

    }

    @RequestMapping("/cancel")
    public String cancelPayment (
            @RequestBody String paymentKey,
            @RequestParam String cancelReason,
            Model model) throws JsonProcessingException {
        tossPaymentsService.cancelPayment(paymentKey,cancelReason);

        return "cancel";
    }

}

package com.Soo_Shinsa.controller;


import com.Soo_Shinsa.dto.payment.PaymentApproveRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentResponseDto;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.service.TossPaymentsService;

import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class TossPaymentsController {
    private final TossPaymentsService tossPaymentsService;

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

    @PostMapping("/approve")
    public ResponseEntity<PaymentResponseDto> approvePayment (
            @Valid @RequestBody PaymentApproveRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = UserUtils.getUser(userDetails);
        PaymentResponseDto responseDto = tossPaymentsService.approvePayment(requestDto, user) ;
        return ResponseEntity.ok(responseDto);
    }
}

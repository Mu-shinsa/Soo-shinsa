package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.PaymentRequestDto;
import com.Soo_Shinsa.dto.PaymentResponseDto;

import com.Soo_Shinsa.service.TossPaymentsService;

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
        PaymentResponseDto responseDto = tossPaymentsService.createPayment(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}

package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.PaymentRequestDto;
import com.Soo_Shinsa.dto.PaymentResponseDto;

public interface TossPaymentsService {
    PaymentResponseDto createPayment(PaymentRequestDto requestDto);
}

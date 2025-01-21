package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.PaymentRequestDto;
import com.Soo_Shinsa.dto.PaymentResponseDto;
import com.Soo_Shinsa.model.User;

public interface TossPaymentsService {
    PaymentResponseDto createPayment(PaymentRequestDto requestDto, User user);
}

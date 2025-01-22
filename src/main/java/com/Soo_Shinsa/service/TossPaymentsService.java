package com.Soo_Shinsa.service;


import com.Soo_Shinsa.dto.order.PaymentRequestDto;
import com.Soo_Shinsa.dto.order.PaymentResponseDto;
import com.Soo_Shinsa.model.User;

public interface TossPaymentsService {
    PaymentResponseDto createPayment(PaymentRequestDto requestDto, User user);
}

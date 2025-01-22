package com.Soo_Shinsa.service;


import com.Soo_Shinsa.dto.payment.PaymentApproveRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentRequestDto;
import com.Soo_Shinsa.dto.payment.PaymentResponseDto;
import com.Soo_Shinsa.model.User;

public interface TossPaymentsService {
    PaymentResponseDto createPayment(PaymentRequestDto requestDto, User user);
    PaymentResponseDto approvePayment(PaymentApproveRequestDto requestDto, User user);
}

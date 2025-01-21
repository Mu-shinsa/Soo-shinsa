package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.TossPayMethod;
import com.Soo_Shinsa.constant.TossPayStatus;
import com.Soo_Shinsa.dto.PaymentRequestDto;
import com.Soo_Shinsa.dto.PaymentResponseDto;
import com.Soo_Shinsa.entity.Orders;
import com.Soo_Shinsa.entity.Payment;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.OrdersRepository;
import com.Soo_Shinsa.repository.PaymentRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.TossPaymentsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TossPaymentsServiceImpl implements TossPaymentsService {
    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;


    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto,User user) {
        // 주문 및 사용자 조회
        Orders order = ordersRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("오더가 없습니다"));


        // Payment 엔티티 생성
        Payment payment = new Payment(
                requestDto.getPaymentKey(),
                order.getTotalPrice(),
                TossPayStatus.READY,
                TossPayMethod.Card,
                order,
                user
                );

        Payment savedPayment = paymentRepository.save(payment);

        // 응답 DTO 생성 및 반환
        return PaymentResponseDto.toDto(savedPayment);
    }
}

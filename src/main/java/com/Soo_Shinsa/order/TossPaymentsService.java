package com.Soo_Shinsa.order;




import com.Soo_Shinsa.order.dto.PaymentRequestDto;
import com.Soo_Shinsa.order.dto.PaymentResponseDto;
import com.Soo_Shinsa.order.dto.UserOrderDTO;
import com.Soo_Shinsa.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ui.Model;



public interface TossPaymentsService {
    PaymentResponseDto createPayment(PaymentRequestDto requestDto, User user);
    void approvePayment(String paymentKey, String orderId, Long amount, Model model) throws JsonProcessingException;
    UserOrderDTO findItem(Long userId, Long orderId);
    void cancelPayment(String paymentKey, String cancelReason) throws JsonProcessingException;
}

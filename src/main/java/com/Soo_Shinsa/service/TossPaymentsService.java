package com.Soo_Shinsa.service;



import com.Soo_Shinsa.dto.payment.UserOrderDTO;
import com.Soo_Shinsa.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ui.Model;



public interface TossPaymentsService {
    void approvePayment(User user, String paymentKey, String orderId, Long amount, Model model) throws JsonProcessingException;
    UserOrderDTO findItem(Long userId, Long orderId);
}

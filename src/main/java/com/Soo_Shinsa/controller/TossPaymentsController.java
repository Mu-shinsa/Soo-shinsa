package com.Soo_Shinsa.controller;



import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.service.TossPaymentsService;

import com.Soo_Shinsa.utils.UserUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TossPaymentsController {
    private final TossPaymentsService tossPaymentsService;

    @Value("${toss.secret-key}")
    private String secretKey;

    @Value("${toss.client-key}")
    private String clientKey;


    @RequestMapping("/success")
    public String approvePayment (
            @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount,
            @AuthenticationPrincipal UserDetails userDetails, Model model) throws JsonProcessingException {
        model.addAttribute("tosspayments_key", clientKey);
        User user = UserUtils.getUser(userDetails);
        tossPaymentsService.approvePayment(user,paymentKey,orderId,amount,model);
        return "success";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

}

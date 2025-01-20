package com.Soo_Shinsa.controller;


import com.Soo_Shinsa.dto.OrderItemRequestDto;
import com.Soo_Shinsa.dto.OrdersRequestDto;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.dto.SingleProductOrderRequestDto;
import com.Soo_Shinsa.service.CartItemService;
import com.Soo_Shinsa.service.OrdersService;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    //특정유저의 특정 오더 읽기
    @GetMapping("/{orderId}/users/{userId}")
    public ResponseEntity<OrdersResponseDto> getOrderById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId) {
        UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.getOrderById(orderId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //단품 구매 생성
//    @PostMapping("/users/{userId}/single-order")
//    public ResponseEntity<OrdersResponseDto> createSingleProductOrder(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @PathVariable Long userId,
//            @Valid
//            @RequestBody SingleProductOrderRequestDto requestDto) {
//        OrdersResponseDto response = ordersService.createSingleProductOrder(userId, requestDto.getProductId(), requestDto.getQuantity(),UserUtils.getUser(userDetails));
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
    @PostMapping("/users/{userId}/from-cart")
    public ResponseEntity<OrdersResponseDto> createOrderFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long userId) {
        OrdersResponseDto responseDto = ordersService.createOrderFromCart(userId,UserUtils.getUser(userDetails));
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrdersResponseDto> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid
            @RequestBody OrdersRequestDto requestDto) {
        OrdersResponseDto responseDto = ordersService.createOrder(requestDto.getUserId(),requestDto.getOrderItems());
        return ResponseEntity.ok(responseDto);
    }

}

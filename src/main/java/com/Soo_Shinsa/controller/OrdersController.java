package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.OrderItemRequestDto;
import com.Soo_Shinsa.dto.OrdersRequestDto;
import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

//    @PostMapping
//    public ResponseEntity<OrdersResponseDto> createOrder(
//            @RequestBody OrdersRequestDto requestDto,
//            @PathVariable Long userId) {
//        ordersService.create(requestDto.getOrderNumber(), userId);
//
//    }

    @GetMapping("/{orderId}/users/{userId}")
    public ResponseEntity<OrdersResponseDto> getOrderById(
            @PathVariable Long userId,
            @PathVariable Long orderId) {
        OrdersResponseDto responseDto = ordersService.getOrderById(userId,orderId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

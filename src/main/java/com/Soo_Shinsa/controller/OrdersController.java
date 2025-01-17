package com.Soo_Shinsa.controller;


import com.Soo_Shinsa.dto.OrdersResponseDto;
import com.Soo_Shinsa.dto.SingleProductOrderRequestDto;
import com.Soo_Shinsa.service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;


    @GetMapping("/{orderId}/users/{userId}")
    public ResponseEntity<OrdersResponseDto> getOrderById(
            @PathVariable Long userId,
            @PathVariable Long orderId) {
        OrdersResponseDto responseDto = ordersService.getOrderById(userId,orderId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/single-order")
    public ResponseEntity<OrdersResponseDto> createSingleProductOrder(
            @PathVariable Long userId,
            @Valid
            @RequestBody SingleProductOrderRequestDto requestDto) {
        OrdersResponseDto response = ordersService.createSingleProductOrder(userId, requestDto.getProductId(), requestDto.getQuantity());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

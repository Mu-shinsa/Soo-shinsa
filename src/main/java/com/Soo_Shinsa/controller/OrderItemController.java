package com.Soo_Shinsa.controller;


import com.Soo_Shinsa.dto.OrderItemRequestDto;
import com.Soo_Shinsa.dto.OrderItemResponseDto;
import com.Soo_Shinsa.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderitems")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<OrderItemResponseDto> createOrderItem(
            @RequestBody OrderItemRequestDto requestDto,
            @PathVariable Long userId) {
        OrderItemResponseDto orderItem = orderItemService.createOrderItem(requestDto.getOrderId(), requestDto.getProductId(), requestDto.getQuantity(), userId);
        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }

    @GetMapping("/{OrderItemsId}/users/{userId}")
    public ResponseEntity<OrderItemResponseDto> findById(
            @PathVariable Long OrderItemsId,
            @PathVariable Long userId){
        OrderItemResponseDto findOrder = orderItemService.findById(OrderItemsId, userId);
        return new ResponseEntity<>(findOrder, HttpStatus.OK);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderItemResponseDto>> readOrderItem(
            @PathVariable Long userId) {
        List<OrderItemResponseDto> byAll = orderItemService.findByAll(userId);
        return new ResponseEntity<>(byAll, HttpStatus.OK);
    }
    @PatchMapping("/{orderItemsId}/users/{userId}")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(
            @PathVariable Long orderItemsId,
            @PathVariable Long userId,
            @RequestBody OrderItemRequestDto dto) {
        OrderItemResponseDto update = orderItemService.update(orderItemsId, userId, dto.getQuantity());
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    @DeleteMapping("/{orderItemsId}/users/{userId}")
    public ResponseEntity<OrderItemResponseDto> delete(
            @PathVariable Long orderItemsId,
            @PathVariable Long userId){
        OrderItemResponseDto delete = orderItemService.delete(orderItemsId, userId);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}

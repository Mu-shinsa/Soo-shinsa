package com.Soo_Shinsa.controller;


import com.Soo_Shinsa.dto.OrderItemRequestDto;
import com.Soo_Shinsa.dto.OrderItemResponseDto;
import com.Soo_Shinsa.service.OrderItemService;
import jakarta.validation.Valid;
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

    //오더 아이템 생성
    @PostMapping("/users/{userId}")
    public ResponseEntity<OrderItemResponseDto> createOrderItem(
            @Valid
            @RequestBody OrderItemRequestDto requestDto,
            @PathVariable Long userId) {
        OrderItemResponseDto orderItem = orderItemService.createOrderItem(requestDto.getOrderId(), requestDto.getProductId(), requestDto.getQuantity(), userId);
        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }
    //특정유저의 특정 오더아이템 읽기
    @GetMapping("/{OrderItemsId}/users/{userId}")
    public ResponseEntity<OrderItemResponseDto> findById(
            @PathVariable Long OrderItemsId,
            @PathVariable Long userId){
        OrderItemResponseDto findOrder = orderItemService.findById(OrderItemsId, userId);
        return new ResponseEntity<>(findOrder, HttpStatus.OK);
    }
    //특정 유저의 모든 오더아이템들을 읽기
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderItemResponseDto>> readOrderItem(
            @PathVariable Long userId) {
        List<OrderItemResponseDto> byAll = orderItemService.findByAll(userId);
        return new ResponseEntity<>(byAll, HttpStatus.OK);
    }
    //특정 유저의 특정 오더아이템 수정
    @PatchMapping("/{orderItemsId}/users/{userId}")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(
            @PathVariable Long orderItemsId,
            @PathVariable Long userId,
            @Valid
            @RequestBody OrderItemRequestDto dto) {
        OrderItemResponseDto update = orderItemService.update(orderItemsId, userId, dto.getQuantity());
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    //특정유저의 특정 오더 아이템 삭제
    @DeleteMapping("/{orderItemsId}/users/{userId}")
    public ResponseEntity<OrderItemResponseDto> delete(
            @PathVariable Long orderItemsId,
            @PathVariable Long userId){
        OrderItemResponseDto delete = orderItemService.delete(orderItemsId, userId);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}

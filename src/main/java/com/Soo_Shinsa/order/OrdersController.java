package com.Soo_Shinsa.controller;



import com.Soo_Shinsa.dto.order.OrdersResponseDto;
import com.Soo_Shinsa.dto.order.OrdersUpdateRequestDto;

import com.Soo_Shinsa.dto.product.SingleProductOrderRequestDto;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.service.OrdersService;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    //특정유저의 특정 오더 읽기
    @GetMapping("/{orderId}/users")
    public ResponseEntity<OrdersResponseDto> getOrderById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.getOrderById(orderId,user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //특정유저의 모든 오더 읽기
    @GetMapping("/users")
    public ResponseEntity<Page<OrdersResponseDto>> getOrderByAll(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        User user = UserUtils.getUser(userDetails);
        Page<OrdersResponseDto> allByUserId = ordersService.getAllByUserId(user, pageable);
        return new ResponseEntity<>(allByUserId, HttpStatus.OK);
    }

//    단품 구매 생성
    @PostMapping("/users/single-order")
    public ResponseEntity<OrdersResponseDto> createSingleProductOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid
            @RequestBody SingleProductOrderRequestDto requestDto) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto response = ordersService.createSingleProductOrder(user,requestDto.getProductId(), requestDto.getQuantity());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/users/from-cart")
    public ResponseEntity<OrdersResponseDto> createOrderFromCart(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.createOrderFromCart(user);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<OrdersResponseDto> createOrder(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.createOrder(user);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }
    @PatchMapping("/users")
    public ResponseEntity<OrdersResponseDto> updateOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid
            @RequestBody OrdersUpdateRequestDto requestDto){
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.updateOrder(user, requestDto.getOrderId(), requestDto.getStatus());
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

}

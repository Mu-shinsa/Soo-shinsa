package com.Soo_Shinsa.order;


import com.Soo_Shinsa.order.dto.OrdersRequestDto;
import com.Soo_Shinsa.order.dto.OrdersResponseDto;
import com.Soo_Shinsa.product.dto.SingleProductOrderRequestDto;
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
    @GetMapping("/{orderId}/users/{userId}")
    public ResponseEntity<OrdersResponseDto> getOrderById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId,
            @PathVariable Long userId) {
        UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.getOrderById(orderId,userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //특정유저의 모든 오더 읽기
    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<OrdersResponseDto>> getOrderByAll(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        UserUtils.getUser(userDetails);
        Page<OrdersResponseDto> allByUserId = ordersService.getAllByUserId(userId, pageable);
        return new ResponseEntity<>(allByUserId, HttpStatus.OK);
    }

//    단품 구매 생성
    @PostMapping("/users/{userId}/single-order")
    public ResponseEntity<OrdersResponseDto> createSingleProductOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long userId,
            @Valid
            @RequestBody SingleProductOrderRequestDto requestDto) {
        UserUtils.getUser(userDetails);
        OrdersResponseDto response = ordersService.createSingleProductOrder(userId, requestDto.getProductId(), requestDto.getQuantity());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/users/{userId}/from-cart")
    public ResponseEntity<OrdersResponseDto> createOrderFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long userId) {
        UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.createOrderFromCart(userId);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<OrdersResponseDto> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid
            @RequestBody OrdersRequestDto requestDto) {
        UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.createOrder(requestDto.getUserId());
        return ResponseEntity.ok(responseDto);
    }

}

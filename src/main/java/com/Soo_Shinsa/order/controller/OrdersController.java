package com.Soo_Shinsa.order.controller;


import com.Soo_Shinsa.order.dto.OrderDateRequestDto;
import com.Soo_Shinsa.order.dto.OrdersResponseDto;
import com.Soo_Shinsa.order.dto.OrdersUpdateRequestDto;
import com.Soo_Shinsa.order.service.OrdersService;
import com.Soo_Shinsa.product.dto.SingleProductOrderRequestDto;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.CommonResponse;
import com.Soo_Shinsa.utils.ResponseMessage;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrdersResponseDto>> getOrderById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderId) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.getOrderById(orderId, user);
        CommonResponse<OrdersResponseDto> response = new CommonResponse<>(ResponseMessage.ORDER_SELECT_SUCCESS, responseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //특정유저의 모든 오더 읽기
    @GetMapping("/users")
    public ResponseEntity<CommonResponse<Page<OrdersResponseDto>>> getOrderByAll(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @RequestBody OrderDateRequestDto dateRequestDto,
                                                                 @RequestParam (defaultValue = "0") int page,
                                                                 @RequestParam (defaultValue = "10") int size) {
        User user = UserUtils.getUser(userDetails);
        Page<OrdersResponseDto> allByUserId = ordersService.getAllByUserId(user, dateRequestDto, page, size);
        CommonResponse<Page<OrdersResponseDto>> response = new CommonResponse<>(ResponseMessage.ORDER_SELECT_SUCCESS, allByUserId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //    단품 구매 생성
    @PostMapping("/single")
    public ResponseEntity<CommonResponse<OrdersResponseDto>> createSingleProductOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @Valid @RequestBody SingleProductOrderRequestDto requestDto) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto response = ordersService.createSingleProductOrder(user, requestDto.getProductId(), requestDto.getQuantity());
        CommonResponse<OrdersResponseDto> commonResponse = new CommonResponse<>(ResponseMessage.ORDER_CREATE_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    //카트에 담음 물건을 구매 생성
    @PostMapping("/carts")
    public ResponseEntity<CommonResponse<OrdersResponseDto>> createAllOrderFromCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.createAllOrderFromCart(user);
        CommonResponse<OrdersResponseDto> response = new CommonResponse<>(ResponseMessage.ORDER_CREATE_SUCCESS, responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //오더 단일 생성
    @PostMapping
    public ResponseEntity<CommonResponse<OrdersResponseDto>> createOrder(@AuthenticationPrincipal UserDetails userDetails) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.createOrder(user);
        CommonResponse<OrdersResponseDto> response = new CommonResponse<>(ResponseMessage.ORDER_CREATE_SUCCESS, responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //오더 수정
    @PatchMapping
    public ResponseEntity<CommonResponse<OrdersResponseDto>> updateOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                         @Valid @RequestBody OrdersUpdateRequestDto requestDto) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.updateOrder(user, requestDto.getOrderId(), requestDto.getStatus());
        CommonResponse<OrdersResponseDto> response = new CommonResponse<>(ResponseMessage.ORDER_UPDATE_SUCCESS, responseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/carts/{cartId}")
    public ResponseEntity<CommonResponse<OrdersResponseDto>> createOrderFromCart(@AuthenticationPrincipal UserDetails userDetails,
                                                                                 @PathVariable Long cartId) {
        User user = UserUtils.getUser(userDetails);
        OrdersResponseDto responseDto = ordersService.createSingleOrderCartItem(user, cartId);
        CommonResponse<OrdersResponseDto> response = new CommonResponse<>(ResponseMessage.ORDER_CREATE_SUCCESS, responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

package com.Soo_Shinsa.order;




import com.Soo_Shinsa.order.dto.OrderItemRequestDto;
import com.Soo_Shinsa.order.dto.OrderItemResponseDto;
import com.Soo_Shinsa.utils.user.model.User;
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
@RequestMapping("/orderitems")
public class OrderItemController {
    private final OrderItemService orderItemService;

    //오더 아이템 생성
    @PostMapping("/users")
    public ResponseEntity<OrderItemResponseDto> createOrderItem(
            @Valid
            @RequestBody OrderItemRequestDto requestDto) {
        OrderItemResponseDto orderItem = orderItemService.createOrderItem(requestDto);
        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }
    //특정유저의 특정 오더아이템 읽기
    @GetMapping("/{OrderItemsId}/users")
    public ResponseEntity<OrderItemResponseDto> findById(
            @PathVariable Long OrderItemsId){
        OrderItemResponseDto findOrder = orderItemService.findById(OrderItemsId);
        return new ResponseEntity<>(findOrder, HttpStatus.OK);
    }
    //특정 유저의 모든 오더아이템들을 읽기
    @GetMapping("/users")
    public ResponseEntity<List<OrderItemResponseDto>> readOrderItem(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = UserUtils.getUser(userDetails);
        List<OrderItemResponseDto> byAll = orderItemService.findByAll(user);
        return new ResponseEntity<>(byAll, HttpStatus.OK);
    }
    //특정 유저의 특정 오더아이템 수정
    @PatchMapping("/{orderItemsId}/users")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderItemsId,
            @Valid
            @RequestBody OrderItemRequestDto dto) {
        User user = UserUtils.getUser(userDetails);
        OrderItemResponseDto update = orderItemService.update(orderItemsId, dto.getQuantity(),user);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    //특정유저의 특정 오더 아이템 삭제
    @DeleteMapping("/{orderItemsId}/users")
    public ResponseEntity<OrderItemResponseDto> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long orderItemsId){
        User user = UserUtils.getUser(userDetails);
        OrderItemResponseDto delete = orderItemService.delete(orderItemsId,user);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}

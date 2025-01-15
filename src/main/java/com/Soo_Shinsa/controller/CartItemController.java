package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.CartItemRequestDto;
import com.Soo_Shinsa.dto.CartItemResponseDto;
import com.Soo_Shinsa.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartItemController {
    private CartItemService cartItemService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<CartItemResponseDto> createCart(
        @RequestBody CartItemRequestDto dto,
        @PathVariable Long userId) {

        CartItemResponseDto saved = cartItemService.create(dto.getOptionId(), dto.getQuantity(), userId);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @GetMapping("/{cartId}/users/{userId}")
    public ResponseEntity<CartItemResponseDto> findById(
            @PathVariable Long cartId,
            @PathVariable Long userId){
        CartItemResponseDto findCart = cartItemService.findById(cartId, userId);
        return new ResponseEntity<>(findCart, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<CartItemResponseDto>> findById(
            @PathVariable Long userId){
        List<CartItemResponseDto> byAll = cartItemService.findByAll(userId);
        return new ResponseEntity<>(byAll, HttpStatus.OK);
    }


    @PatchMapping("/{cartId}/users/{userId}")
    public ResponseEntity<CartItemResponseDto> update(
            @PathVariable Long cartId,
            @PathVariable Long userId,
            @RequestBody CartItemRequestDto dto){
        CartItemResponseDto saved = cartItemService.update(cartId, userId, dto.getQuantity());
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/users/{userId}")
    public String delete(
            @PathVariable Long cartId,
            @PathVariable Long userId,
            @RequestBody CartItemRequestDto dto){
        cartItemService.delete(cartId, userId);
        return "장바구니가 삭제되었습니다.";
    }
}

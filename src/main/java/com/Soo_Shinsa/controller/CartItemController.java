package com.Soo_Shinsa.controller;


import com.Soo_Shinsa.dto.CartItemRequestDto;
import com.Soo_Shinsa.dto.CartItemResponseDto;
import com.Soo_Shinsa.service.CartItemService;
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
@RequestMapping("/carts")
public class CartItemController {
    private final CartItemService cartItemService;

    //카트 생성
    @PostMapping("/users/{userId}")
    public ResponseEntity<CartItemResponseDto> createCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid
            @RequestBody CartItemRequestDto dto,
            @PathVariable Long userId) {
        UserUtils.getUser(userDetails);
        CartItemResponseDto saved = cartItemService.create(dto.getOptionId(), dto.getQuantity(), userId);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    //해당 유저의 특정카트를 읽음
    @GetMapping("/{cartId}/users/{userId}")
    public ResponseEntity<CartItemResponseDto> findById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartId,
            @PathVariable Long userId){
        UserUtils.getUser(userDetails);
        CartItemResponseDto findCart = cartItemService.findById(cartId, userId);
        return new ResponseEntity<>(findCart, HttpStatus.OK);
    }


    //유저의 카트들을 모두 검색
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<CartItemResponseDto>> findByIdAll(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long userId){
        UserUtils.getUser(userDetails);
        List<CartItemResponseDto> byAll = cartItemService.findByAll(userId);
        return new ResponseEntity<>(byAll, HttpStatus.OK);
    }


    //특정 유저의 특정 카트 변경
    @PatchMapping("/{cartId}/users/{userId}")
    public ResponseEntity<CartItemResponseDto> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartId,
            @PathVariable Long userId,
            @Valid
            @RequestBody CartItemRequestDto dto){
        UserUtils.getUser(userDetails);
        CartItemResponseDto saved = cartItemService.update(cartId, userId, dto.getQuantity());
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
    //특정 유저의 특정카트 삭제
    @DeleteMapping("/{cartId}/users/{userId}")
    public ResponseEntity<CartItemResponseDto> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartId,
            @PathVariable Long userId){
        UserUtils.getUser(userDetails);
        CartItemResponseDto deleteCartItem = cartItemService.delete(cartId, userId);
        return new ResponseEntity<>(deleteCartItem,HttpStatus.OK);
    }
}
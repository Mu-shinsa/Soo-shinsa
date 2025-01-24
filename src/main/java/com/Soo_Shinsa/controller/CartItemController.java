package com.Soo_Shinsa.controller;



import com.Soo_Shinsa.dto.cartitem.CartItemRequestDto;
import com.Soo_Shinsa.dto.cartitem.CartItemResponseDto;
import com.Soo_Shinsa.model.User;
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
    @PostMapping("/users")
    public ResponseEntity<CartItemResponseDto> createCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid
            @RequestBody CartItemRequestDto dto) {
        User user = UserUtils.getUser(userDetails);
        CartItemResponseDto saved = cartItemService.create(dto.getOptionId(), dto.getQuantity(), user);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    //해당 유저의 특정카트를 읽음
    @GetMapping("/{cartId}/users")
    public ResponseEntity<CartItemResponseDto> findById(
            @PathVariable Long cartId){
        CartItemResponseDto findCart = cartItemService.findById(cartId);
        return new ResponseEntity<>(findCart, HttpStatus.OK);
    }


    //유저의 카트들을 모두 검색
    @GetMapping("/users")
    public ResponseEntity<List<CartItemResponseDto>> findByIdAll(
            @AuthenticationPrincipal UserDetails userDetails){
        User user = UserUtils.getUser(userDetails);
        List<CartItemResponseDto> byAll = cartItemService.findByAll(user);
        return new ResponseEntity<>(byAll, HttpStatus.OK);
    }


    //특정 유저의 특정 카트 변경
    @PatchMapping("/{cartId}/users")
    public ResponseEntity<CartItemResponseDto> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartId,
            @Valid
            @RequestBody CartItemRequestDto dto){
        User user = UserUtils.getUser(userDetails);
        CartItemResponseDto saved = cartItemService.update(cartId, dto.getQuantity(),user);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
    //특정 유저의 특정카트 삭제
    @DeleteMapping("/{cartId}/users")
    public ResponseEntity<CartItemResponseDto> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartId){
        User user = UserUtils.getUser(userDetails);
        CartItemResponseDto deleteCartItem = cartItemService.delete(cartId,user);
        return new ResponseEntity<>(deleteCartItem,HttpStatus.OK);
    }
}
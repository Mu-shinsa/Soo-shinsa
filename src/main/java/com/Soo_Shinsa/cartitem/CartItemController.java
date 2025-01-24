package com.Soo_Shinsa.cartitem;


import com.Soo_Shinsa.cartitem.dto.CartItemRequestDto;
import com.Soo_Shinsa.cartitem.dto.CartItemResponseDto;
import com.Soo_Shinsa.user.model.User;
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
@RequestMapping("/carts")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemResponseDto> createCart(@AuthenticationPrincipal UserDetails userDetails,
                                                          @Valid @RequestBody CartItemRequestDto dto) {
        User user = UserUtils.getUser(userDetails);
        CartItemResponseDto saved = cartItemService.create(user, dto);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @GetMapping("/{cartId}")
    public ResponseEntity<CartItemResponseDto> findById(@AuthenticationPrincipal UserDetails userDetails,
                                                        @PathVariable Long cartId){
        User user = UserUtils.getUser(userDetails);
        CartItemResponseDto findCart = cartItemService.findById(cartId, user);
        return new ResponseEntity<>(findCart, HttpStatus.OK);
    }


    //유저의 카트들을 모두 검색
    @GetMapping("/users")
    public ResponseEntity<Page<CartItemResponseDto>> findByIdAll(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        User user = UserUtils.getUser(userDetails);
        Page<CartItemResponseDto> cartItems = cartItemService.findByAll(user.getUserId(), pageable);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }


    @PatchMapping("/{cartId}")
    public ResponseEntity<CartItemResponseDto> update(@AuthenticationPrincipal UserDetails userDetails,
                                                      @PathVariable Long cartId,
                                                      @Valid @RequestBody CartItemRequestDto dto){
        User user = UserUtils.getUser(userDetails);
        CartItemResponseDto saved = cartItemService.update(cartId, user, dto.getQuantity());
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long cartId){
        User user = UserUtils.getUser(userDetails);
        cartItemService.delete(cartId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
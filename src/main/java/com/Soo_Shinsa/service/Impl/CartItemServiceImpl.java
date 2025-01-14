package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.dto.CartItemResponseDto;
import com.Soo_Shinsa.entity.CartItem;
import com.Soo_Shinsa.entity.ProductOption;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.CartItemRepository;
import com.Soo_Shinsa.repository.ProcductOptionRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProcductOptionRepository procductOptionRepository;

    @Override
    public CartItemResponseDto create(Long optionId,Integer quantity,Long userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        User user = userDetails.getUser();

        Optional<User> loginId = userRepository.findById(user.getUserId());

        if(!loginId.get().getUserId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        Optional<ProductOption> findOption = procductOptionRepository.findById(optionId);

        CartItem cartItem = new CartItem(quantity,user,findOption.get());

        return CartItemResponseDto.toDto(cartItem);
    }

    @Override
    public CartItemResponseDto findById(Long cartId, Long userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        User user = userDetails.getUser();

        Optional<User> loginId = userRepository.findById(user.getUserId());

        if(!loginId.get().getUserId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        CartItem savedCart = findByIdOrElseThrow(cartId);
        return CartItemResponseDto.toDto(savedCart);

    }

    @Override
    public CartItem findByIdOrElseThrow(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public CartItemResponseDto update(Long cartId, Long userId,Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        User user = userDetails.getUser();

        Optional<User> loginId = userRepository.findById(user.getUserId());

        if(!loginId.get().getUserId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        CartItem findCart = findByIdOrElseThrow(cartId);
        findCart.updateCartItem(quantity);

        CartItem saved = cartItemRepository.save(findCart);
        return CartItemResponseDto.toDto(saved);
    }

    @Override
    public void delete(Long cartId, Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        User user = userDetails.getUser();

        Optional<User> loginId = userRepository.findById(user.getUserId());

        if(!loginId.get().getUserId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        CartItem findCart = findByIdOrElseThrow(cartId);
        cartItemRepository.delete(findCart);

    }


}

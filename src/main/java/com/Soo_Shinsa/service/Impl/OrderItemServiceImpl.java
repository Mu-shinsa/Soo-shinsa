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
import com.Soo_Shinsa.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProcductOptionRepository procductOptionRepository;

}

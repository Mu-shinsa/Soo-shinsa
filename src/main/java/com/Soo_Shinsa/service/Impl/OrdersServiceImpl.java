package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.repository.CartItemRepository;
import com.Soo_Shinsa.repository.ProcductOptionRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.OrderItemService;
import com.Soo_Shinsa.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProcductOptionRepository procductOptionRepository;

}

package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;
}

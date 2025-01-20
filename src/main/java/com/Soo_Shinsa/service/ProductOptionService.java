package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.model.User;

import java.util.List;

public interface ProductOptionService {
    ProductOptionResponseDto createOption(User user, ProductOptionRequestDto productOptionRequestDto, Long productId);

    ProductOptionResponseDto updateOption(User user, ProductOptionRequestDto productOptionRequestDto, Long productOptionId);

    ProductOptionResponseDto findOption(Long productOptionId);

    List<ProductOptionResponseDto> findOptionListByProductId(Long productId);
}

package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.product.ProductOptionRequestDto;
import com.Soo_Shinsa.dto.product.ProductOptionResponseDto;
import com.Soo_Shinsa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductOptionService {
    ProductOptionResponseDto createOption(User user, ProductOptionRequestDto productOptionRequestDto, Long productId);

    ProductOptionResponseDto updateOption(User user, ProductOptionRequestDto productOptionRequestDto, Long productOptionId);

    ProductOptionResponseDto findOption(Long productOptionId);

    Page<ProductOptionResponseDto> findProductsByOptionalSizeAndColor(ProductOptionRequestDto requestDto, Pageable pageable);
}
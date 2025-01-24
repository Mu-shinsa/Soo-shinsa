package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.utils.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductOptionService {
    ProductOptionResponseDto createOption(User user, ProductOptionRequestDto productOptionRequestDto, Long productId);

    ProductOptionResponseDto updateOption(User user, ProductOptionRequestDto productOptionRequestDto, Long productOptionId);

    ProductOptionResponseDto findOption(Long productOptionId);

    Page<ProductOptionResponseDto> findProductsByOptionalSizeAndColor(ProductOptionRequestDto requestDto, Pageable pageable);
}
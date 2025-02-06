package com.Soo_Shinsa.product.repository;

import com.Soo_Shinsa.product.dto.FindProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductOptionCustomRepository {
    Page<ProductOptionResponseDto> findProductsByOptionalSizeAndColor(FindProductOptionRequestDto requestDto, Pageable pageable);
}

package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.dto.FindProductRequestDto;
import com.Soo_Shinsa.product.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
    Page<ProductResponseDto> findAllProduct(Long brandId, FindProductRequestDto requestDto, Pageable pageable);
}

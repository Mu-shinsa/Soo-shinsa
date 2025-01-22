package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.product.ProductRequestDto;
import com.Soo_Shinsa.dto.product.ProductResponseDto;
import com.Soo_Shinsa.model.User;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(User user, ProductRequestDto productRequestDto, Long brandId);

    ProductResponseDto updateProduct(User user, ProductRequestDto productRequestDto, Long productId);

    ProductResponseDto findProduct(Long productId);

    List<ProductResponseDto> findProductListByBrandId(Long brandId);

    List<ProductResponseDto> findAllProduct();
}

package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.product.FindProductResponseDto;
import com.Soo_Shinsa.dto.product.ProductRequestDto;
import com.Soo_Shinsa.dto.product.ProductResponseDto;
import com.Soo_Shinsa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

    ProductResponseDto createProduct(User user, ProductRequestDto dto, Long brandId, MultipartFile imageFile);

    ProductResponseDto updateProduct(User user, ProductRequestDto dto, Long productId, MultipartFile imageFile);

    FindProductResponseDto findProduct(Long productId);

    Page<ProductResponseDto> findAllProductByBrandId(Long brandId, Pageable pageable);

    Page<ProductResponseDto> findAllProduct(Pageable pageable);

    void deleteProduct(Long productId, User user);
}


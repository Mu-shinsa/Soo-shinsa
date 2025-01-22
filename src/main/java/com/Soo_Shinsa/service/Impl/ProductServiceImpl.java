package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.dto.product.ProductRequestDto;
import com.Soo_Shinsa.dto.product.ProductResponseDto;
import com.Soo_Shinsa.model.Brand;
import com.Soo_Shinsa.model.Product;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.BrandRepository;
import com.Soo_Shinsa.repository.ProductRepository;
import com.Soo_Shinsa.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    BrandRepository brandRepository;
    ProductRepository productRepository;

    @Transactional
    @Override
    public ProductResponseDto createProduct(User user, ProductRequestDto dto, Long brand) {

        Role findRole = user.getRole();

        if(Role.CUSTOMER.equals(findRole)) {
            throw new IllegalArgumentException("일반 사용자는 상품 등록을 할 수 없습니다.");
        }

        Brand findBrand = brandRepository.findByIdOrElseThrow(brand);

        Product product = new Product(dto.getName(), dto.getPrice(), dto.getStatus(), findBrand);

        return ProductResponseDto.toDto(product);
    }

    @Transactional
    @Override
    public ProductResponseDto updateProduct(User user, ProductRequestDto dto, Long productId) {

        Product findProduct = productRepository.findByIdOrElseThrow(productId);

        findProduct.update(dto.getName(), dto.getPrice(), dto.getStatus());

        Product savedProduct = productRepository.save(findProduct);

        return ProductResponseDto.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductResponseDto findProduct(Long productId) {

        Product findProduct = productRepository.findByIdOrElseThrow(productId);

        return ProductResponseDto.toDto(findProduct);
    }

    @Transactional
    @Override
    public List<ProductResponseDto> findProductListByBrandId(Long brandId) {

        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);

        List<Product> products = productRepository.findAllByBrandId(findBrand);

        return products.stream().map(ProductResponseDto::toDto).toList();
    }

    // 수정할 예정
    @Transactional
    @Override
    public List<ProductResponseDto> findAllProduct() {

        List<Product> products = productRepository.findAll();

        return products.stream().map(ProductResponseDto::toDto).toList();
    }
}

package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.dto.BrandResponseDto;
import com.Soo_Shinsa.dto.ProductRequestDto;
import com.Soo_Shinsa.dto.ProductResponseDto;
import com.Soo_Shinsa.entity.Brand;
import com.Soo_Shinsa.entity.Product;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.BrandRepository;
import com.Soo_Shinsa.repository.ProductRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.ProductService;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    BrandRepository brandRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @Transactional
    @Override
    public ProductResponseDto createProduct(User user, ProductRequestDto dto, Long brand) {

        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Brand brandId = brandRepository.findById(brand)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브랜드입니다."));

        Role findRole = userById.getRole();

        if(findRole == Role.CUSTOMER) {
            throw new IllegalArgumentException("일반 사용자는 상품 등록을 할 수 없습니다.");
        }
        Product product = new Product(dto.getName(), dto.getPrice(), dto.getStatus(), brandId);

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductResponseDto updateProduct(User user, ProductRequestDto dto, Long productId) {

        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        findProduct.update(dto.getName(), dto.getPrice(), dto.getStatus());

        Product savedProduct = productRepository.save(findProduct);

        return ProductResponseDto.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductResponseDto findProduct(Long productId) {

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return ProductResponseDto.toDto(findProduct);
    }

    @Transactional
    @Override
    public List<ProductResponseDto> findProductListByBrandId(Long brandId) {

        Brand findBrandId = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브랜드입니다."));

        List<Product> products = productRepository.findAllByBrandId(findBrandId);

        return products.stream().map(ProductResponseDto::toDto).toList();
    }

    @Transactional
    @Override
    public List<ProductResponseDto> findAllProduct() {

        List<Product> products = productRepository.findAll();

        return products.stream().map(ProductResponseDto::toDto).toList();
    }
}

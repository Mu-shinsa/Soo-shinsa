package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.dto.ProductResponseDto;
import com.Soo_Shinsa.entity.Brand;
import com.Soo_Shinsa.entity.Product;
import com.Soo_Shinsa.entity.ProductOption;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.BrandRepository;
import com.Soo_Shinsa.repository.ProductOptionRepository;
import com.Soo_Shinsa.repository.ProductRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.ProductOptionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductOptionServiceImpl implements ProductOptionService {

    ProductOptionRepository productOptionRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @Transactional
    @Override
    public ProductOptionResponseDto createOption(User user, ProductOptionRequestDto dto, Long productId) {

        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        Role findRole = userById.getRole();

        if(findRole == Role.CUSTOMER) {
            throw new IllegalArgumentException("일반 사용자는 옵션 등록을 할 수 없습니다.");
        }
        ProductOption option = new ProductOption(dto.getSize(), dto.getColor(), dto.getStatus(), findProduct);

        ProductOption savedOption = productOptionRepository.save(option);

        return ProductOptionResponseDto.toDto(savedOption);
    }

    @Transactional
    @Override
    public ProductOptionResponseDto updateOption(User user, ProductOptionRequestDto dto, Long productOptionId) {

        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        ProductOption findOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));

        findOption.update(dto.getSize(), dto.getColor(), dto.getStatus());

        ProductOption savedOption = productOptionRepository.save(findOption);

        return ProductOptionResponseDto.toDto(savedOption);
    }

    @Transactional
    @Override
    public ProductOptionResponseDto findOption(Long productOptionId) {

        ProductOption findOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));

        return ProductOptionResponseDto.toDto(findOption);
    }

    @Transactional
    @Override
    public List<ProductOptionResponseDto> findOptionListByProductId(Long productId) {

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        List<ProductOption> options = productOptionRepository.findAllByProductId(findProduct);

        return options.stream().map(ProductOptionResponseDto::toDto).toList();
    }

}

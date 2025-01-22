package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.dto.product.ProductOptionRequestDto;
import com.Soo_Shinsa.dto.product.ProductOptionResponseDto;
import com.Soo_Shinsa.model.Product;
import com.Soo_Shinsa.model.ProductOption;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.ProductOptionRepository;
import com.Soo_Shinsa.repository.ProductRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    ProductOptionRepository productOptionRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @Transactional
    @Override
    public ProductOptionResponseDto createOption(User user, ProductOptionRequestDto dto, Long productId) {

        Product findProduct = productRepository.findByIdOrElseThrow(productId);

        Role findRole = user.getRole();

        if(Role.CUSTOMER.equals(findRole)) {
            throw new IllegalArgumentException("일반 사용자는 상품 등록을 할 수 없습니다.");
        }

        ProductOption option = new ProductOption(dto.getSize(), dto.getColor(), dto.getStatus(), findProduct);

        return ProductOptionResponseDto.toDto(option);
    }

    @Transactional
    @Override
    public ProductOptionResponseDto updateOption(User user, ProductOptionRequestDto dto, Long productOptionId) {

        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        ProductOption findOption = productOptionRepository.findByIdOrElseThrow(productOptionId);

        findOption.update(dto.getSize(), dto.getColor(), dto.getStatus());

        ProductOption savedOption = productOptionRepository.save(findOption);

        return ProductOptionResponseDto.toDto(savedOption);
    }

    @Override
    public ProductOptionResponseDto findOption(Long productOptionId) {

        ProductOption findOption = productOptionRepository.findByIdOrElseThrow(productOptionId);

        return ProductOptionResponseDto.toDto(findOption);
    }

    @Override
    public List<ProductOptionResponseDto> findOptionListByProductId(Long productId) {

        Product findProduct = productRepository.findByIdOrElseThrow(productId);

        List<ProductOption> options = productOptionRepository.findAllByProductId(findProduct);

        return options.stream().map(ProductOptionResponseDto::toDto).toList();
    }

}

package com.Soo_Shinsa.product;

import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.product.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.utils.user.model.User;
import com.Soo_Shinsa.utils.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    ProductOptionRepository productOptionRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @Transactional
    @Override
    public ProductOptionResponseDto createOption(User user, ProductOptionRequestDto dto, Long productId) {

        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        checkUserRole(userById);

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));


        ProductOption option = dto.toEntity(findProduct);

        ProductOption savedOption = productOptionRepository.save(option);

        return ProductOptionResponseDto.toDto(savedOption);
    }

    @Transactional
    @Override
    public ProductOptionResponseDto updateOption(User user, ProductOptionRequestDto dto, Long productOptionId) {

        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        checkUserRole(userById);

        ProductOption findOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));


        findOption.update(dto.getSize(), dto.getColor(), dto.getStatus());

        ProductOption savedOption = productOptionRepository.save(findOption);

        return ProductOptionResponseDto.toDto(savedOption);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductOptionResponseDto findOption(Long productOptionId) {

        ProductOption findOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));

        return ProductOptionResponseDto.toDto(findOption);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductOptionResponseDto> findProductsByOptionalSizeAndColor(ProductOptionRequestDto requestDto, Pageable pageable) {

        if (requestDto.getColor() == null && requestDto.getSize() == null) {
            throw new IllegalArgumentException("색상과 사이즈 중 하나는 필수입니다.");
        }

        Page<ProductOption> options = productOptionRepository.findProductsByOptionalSizeAndColor(requestDto.getSize(), requestDto.getColor(), pageable);

        return options.map(ProductOptionResponseDto::toDto);
    }

    private static void checkUserRole(User userById) {
        if (!userById.getRole().equals(Role.ADMIN) && !userById.getRole().equals(Role.VENDOR)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

}


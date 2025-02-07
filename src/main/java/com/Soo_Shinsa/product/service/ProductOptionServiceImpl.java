package com.Soo_Shinsa.product.service;

import com.Soo_Shinsa.exception.ErrorCode;
import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.product.dto.FindProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.product.dto.ProductOptionUpdateDto;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.product.repository.ProductOptionRepository;
import com.Soo_Shinsa.product.repository.ProductRepository;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

   private final ProductOptionRepository productOptionRepository;
   private final ProductRepository productRepository;

    @Transactional
    @Override
    public ProductOptionResponseDto createOption(User user, ProductOptionRequestDto dto, Long productId) {
        EntityValidator.validateAdminOrVendorAccess(user);

        Product findProduct = productRepository.findByIdOrElseThrow(productId);


        ProductOption option = ProductOption.builder()
                .product(findProduct)
                .size(dto.getSize())
                .color(dto.getColor())
                .productStatus(dto.getStatus())
                .product(findProduct)
                .build();

        ProductOption savedOption = productOptionRepository.save(option);

        return ProductOptionResponseDto.toDto(savedOption);
    }

    @Transactional
    @Override
    public ProductOptionResponseDto updateOption(User user, ProductOptionUpdateDto dto, Long productOptionId) {

        EntityValidator.validateAdminOrVendorAccess(user);

        ProductOption findOption = productOptionRepository.findByIdOrElseThrow(productOptionId);

        Product associatedProduct = findOption.getProduct();

        if (associatedProduct == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_PRODUCT);
        }

        findOption.update(dto.getSize(), dto.getColor(), dto.getStatus());


        return ProductOptionResponseDto.toDto(findOption);
    }

    @Override
    public ProductOptionResponseDto findOption(Long productOptionId) {

        ProductOption findOption = productOptionRepository.findByIdOrElseThrow(productOptionId);

        return ProductOptionResponseDto.toDto(findOption);
    }

    @Cacheable(cacheNames = "findProductOptions", key = "'product:options:size:' + #requestDto.size + ':color:' + #requestDto.color + ':page:' + #page + ':size:' + #size")
    @Override
    public Page<ProductOptionResponseDto> findProductsByOptionalSizeAndColor(FindProductOptionRequestDto requestDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productOptionRepository.findProductsByOptionalSizeAndColor(requestDto, pageable);
    }
}


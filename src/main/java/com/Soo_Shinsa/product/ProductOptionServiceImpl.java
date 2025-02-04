package com.Soo_Shinsa.product;

import com.Soo_Shinsa.exception.ErrorCode;
import com.Soo_Shinsa.exception.InvalidInputException;
import com.Soo_Shinsa.exception.NotFoundException;
import com.Soo_Shinsa.product.dto.FindProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.product.dto.ProductOptionUpdateDto;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.product.model.QProductOption;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.Soo_Shinsa.exception.ErrorCode.SELECT_COLOR_OR_SIZE;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

   private final ProductOptionRepository productOptionRepository;
   private final ProductRepository productRepository;
   private final JPAQueryFactory queryFactory;

    @Transactional
    @Override
    public ProductOptionResponseDto createOption(User user, ProductOptionRequestDto dto, Long productId) {
        EntityValidator.validateAdminOrVendorAccess(user);

        Product findProduct = productRepository.findByIdOrElseThrow(productId);


        ProductOption option = ProductOption.builder()
                .product(findProduct)
                .size(dto.getSize())
                .color(dto.getColor())
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

    @Override
    public Page<ProductOptionResponseDto> findProductsByOptionalSizeAndColor(FindProductOptionRequestDto requestDto, int page, int size) {

        QProductOption productOption = QProductOption.productOption;

        // 조건이 모두 없으면 예외 처리
        if (requestDto.getColor() == null && requestDto.getSize() == null) {
            throw new InvalidInputException(SELECT_COLOR_OR_SIZE);
        }

        Pageable pageable = PageRequest.of(page, size);

        // 동적 조건 생성
        BooleanBuilder builder = new BooleanBuilder();

        if (requestDto.getColor() != null) {
            builder.and(productOption.color.eq(requestDto.getColor())); // 색상 조건
        }
        if (requestDto.getSize() != null) {
            builder.and(productOption.size.eq(requestDto.getSize())); // 크기 조건
        }

        List<ProductOption> options = queryFactory
                .selectFrom(productOption)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 계산
        long total = queryFactory
                .select(productOption.count())
                .from(productOption)
                .where(builder)
                .fetch()
                .size();

        // ProductOption → ProductOptionResponseDto 변환
        List<ProductOptionResponseDto> responseDtos = options.stream()
                .map(ProductOptionResponseDto::toDto)
                .toList();

        return new PageImpl<>(responseDtos, pageable, total);
    }

}


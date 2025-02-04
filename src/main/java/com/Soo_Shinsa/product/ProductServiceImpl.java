package com.Soo_Shinsa.product;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.brand.BrandRepository;
import com.Soo_Shinsa.category.CategoryRepository;
import com.Soo_Shinsa.category.model.Category;
import com.Soo_Shinsa.constant.TargetType;
import com.Soo_Shinsa.image.Image;
import com.Soo_Shinsa.image.ImageService;
import com.Soo_Shinsa.order.OrderItemRepository;
import com.Soo_Shinsa.product.dto.*;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.product.model.QProduct;
import com.Soo_Shinsa.review.ReviewRepository;
import com.Soo_Shinsa.user.UserRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final BrandRepository brandRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final ProductOptionRepository productOptionRepository;
    private final JPAQueryFactory queryFactory;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public ProductResponseDto createProduct(User user, ProductRequestDto dto, Long brandId, MultipartFile imageFile) {

        User userById = userRepository.findByIdOrElseThrow(user.getUserId());
        Brand brand = brandRepository.findByIdOrElseThrow(brandId);
        Category category = categoryRepository.findByIdOrElseThrow(dto.getCategoryId());

        EntityValidator.validateAdminOrVendorAccess(userById);

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            Image uploaded = imageService.uploadImage(imageFile, TargetType.PRODUCT, null);
            imageUrl = uploaded.getPath();
        }

        Product product = Product.builder()
                .price(dto.getPrice())
                .name(dto.getName())
                .productStatus(dto.getStatus())
                .brand(brand)
                .category(category)
                .imageUrl(imageUrl)
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductUpdateDto updateProduct(User user, ProductUpdateDto dto, Long productId, MultipartFile imageFile) {


        Product product = productRepository.findByIdOrElseThrow(productId);

        EntityValidator.validateAdminOrVendorAccess(user);

        String newImageUrl = product.getImageUrl(); // 기존 이미지 URL 유지
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지 삭제 후 새로운 이미지 업로드
            Image updatedImage = imageService.updateImage(imageFile, product.getImageUrl(), TargetType.PRODUCT);
            newImageUrl = updatedImage.getPath();
        }

        product.update(dto.getName(), dto.getPrice(), dto.getStatus(), newImageUrl);

        return ProductUpdateDto.toDto(product);
    }

    @Override
    public FindProductResponseDto findProduct(Long productId) {

        Product product = productRepository.findByIdOrElseThrow(productId);

        List<ProductOption> productOptions = productOptionRepository.findProductOptionByProductId(productId);

        return FindProductResponseDto.toDto(product, productOptions);
    }

    @Override
    public Page<ProductResponseDto> findAllProduct(Long brandId, FindProductRequestDto requestDto, int page, int size) {
        Brand brand = brandRepository.findByIdOrElseThrow(brandId);
        Pageable pageable = PageRequest.of(page, size);
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.brand.id.eq(brand.getId()));

        if (requestDto.getNameKeyword() != null && !requestDto.getNameKeyword().isEmpty()) {
            builder.and(product.name.containsIgnoreCase(requestDto.getNameKeyword()));
        }

        if (requestDto.getMinPrice() != null) {
            builder.and(product.price.goe(requestDto.getMinPrice()));
        }

        if (requestDto.getMaxPrice() != null) {
            builder.and(product.price.loe(requestDto.getMaxPrice()));
        }

        if (requestDto.getCategoryId() != null) {
            builder.and(product.category.id.eq(requestDto.getCategoryId()));
        }

        List<Product> products = queryFactory.selectFrom(product)
                .where(builder)
                .orderBy(product.price.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(product)
                .where(builder)
                .fetch()
                .size();


        List<ProductResponseDto> productResponseDtos =
                products.stream().map(ProductResponseDto::toDto).toList();

        return new PageImpl<>(productResponseDtos, pageable, total);
    }

    @Transactional
    public void deleteProduct(Long productId, User user) {
        EntityValidator.validateAdminOrVendorAccess(user);

        Product product = productRepository.findByIdOrElseThrow(productId);

        reviewRepository.deleteAllByProductId(productId);
        orderItemRepository.deleteAllByProductId(productId);
        productOptionRepository.deleteAllByProductId(productId);

        productRepository.delete(product);
    }


}

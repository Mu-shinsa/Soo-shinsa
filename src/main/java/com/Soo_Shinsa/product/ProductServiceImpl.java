package com.Soo_Shinsa.product;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.brand.BrandRepository;
import com.Soo_Shinsa.constant.TargetType;
import com.Soo_Shinsa.image.Image;
import com.Soo_Shinsa.image.ImageService;
import com.Soo_Shinsa.product.dto.FindProductResponseDto;
import com.Soo_Shinsa.product.dto.ProductRequestDto;
import com.Soo_Shinsa.product.dto.ProductResponseDto;
import com.Soo_Shinsa.product.dto.ProductUpdateDto;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Transactional
    @Override
    public ProductResponseDto createProduct(User user, ProductRequestDto dto, Long brandId, MultipartFile imageFile) {

        User userById = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브랜드입니다."));

        userById.validateAdminOrVendorRole();

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
                .imageUrl(imageUrl)
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductUpdateDto updateProduct(User user, ProductUpdateDto dto, Long productId, MultipartFile imageFile) {


        Product product = productRepository.findById(productId, "존재하지 않는 상품입니다.");

        user.validateAdminOrVendorRole();

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

        Product product = productRepository.findById(productId, "존재하지 않는 상품입니다.");

        List<ProductOption> productOptions = productOptionRepository.findProductOptionByProductId(productId);

        return FindProductResponseDto.toDto(product, productOptions);
    }

    @Override
    public Page<ProductResponseDto> findAllProductByBrandId(Long brandId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllProductByBrandId(brandId, pageable);

        return products.map(ProductResponseDto::toDto);
    }

    @Override
    public Page<ProductResponseDto> findAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByBrand(pageable);

        return products.map(ProductResponseDto::toDto);
    }

    @Transactional
    public void deleteProduct(Long productId, User user) {
        user.validateAdminOrVendorRole();

        Product product = productRepository.findById(productId, "존재하지 않는 상품입니다.");

        productOptionRepository.deleteAllByProductId(productId);

        productRepository.delete(product);
    }


}

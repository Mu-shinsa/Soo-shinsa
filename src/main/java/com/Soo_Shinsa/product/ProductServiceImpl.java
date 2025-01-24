package com.Soo_Shinsa.product;

import com.Soo_Shinsa.brand.Brand;
import com.Soo_Shinsa.constant.Role;
import com.Soo_Shinsa.image.Image;
import com.Soo_Shinsa.product.dto.FindProductResponseDto;
import com.Soo_Shinsa.product.dto.ProductRequestDto;
import com.Soo_Shinsa.product.dto.ProductResponseDto;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.product.model.ProductOption;
import com.Soo_Shinsa.brand.BrandRepository;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.user.UserRepository;
import com.Soo_Shinsa.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

        Brand brand = brandRepository.findByIdOrElseThrow(brandId);

        checkUser(user);

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            Image uploaded = imageService.uploadImage(imageFile, "products");
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
    public ProductResponseDto updateProduct(User user, ProductRequestDto dto, Long productId, MultipartFile imageFile) {

        checkUser(user);

        Product product = productRepository.findByIdOrElseThrow(productId);

        String newImageUrl = product.getImageUrl(); // 기존 이미지 URL 유지
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지 삭제 후 새로운 이미지 업로드
            Image updatedImage = imageService.updateImage(imageFile, product.getImageUrl(), "reviews");
            newImageUrl = updatedImage.getPath();
        }

        product.update(dto.getName(), dto.getPrice(), dto.getStatus(), newImageUrl);

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.toDto(savedProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public FindProductResponseDto findProduct(Long productId) {

        Product findProduct = productRepository.findByIdOrElseThrow(productId);

        List<ProductOption> productOptions = productOptionRepository.findAllByProductId(productId);

        return FindProductResponseDto.toDto(findProduct, productOptions);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponseDto> findAllProductByBrandId(Long brandId, Pageable pageable) {

        Page<Product> products = productRepository.findAllProductByBrandId(brandId, pageable);

        return products.map(ProductResponseDto::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponseDto> findAllProduct(Pageable pageable) {

        Page<Product> products = productRepository.findAllByBrand(pageable);

        return products.map(ProductResponseDto::toDto);
    }

    @Transactional
    public void deleteProduct(Long productId, User user) {
        checkUser(user);

        Product product = productRepository.findByIdOrElseThrow(productId);

        productOptionRepository.deleteAllByProductId(productId);

        productRepository.delete(product);
    }

    private static void checkUser(User user) {
        if (!user.getRole().equals(Role.ADMIN) && !user.getRole().equals(Role.VENDOR)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}

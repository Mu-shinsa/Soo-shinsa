package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.dto.FindProductResponseDto;
import com.Soo_Shinsa.product.dto.ProductRequestDto;
import com.Soo_Shinsa.product.dto.ProductResponseDto;
import com.Soo_Shinsa.utils.user.model.User;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/brands/{brandId}")
    public ResponseEntity<ProductResponseDto> createProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                            @Valid @RequestBody ProductRequestDto productRequestDto,
                                                            @RequestPart(required = false) MultipartFile imageFile,
                                                            @PathVariable Long brandId) {

        User user = UserUtils.getUser(userDetails);
        ProductResponseDto product = productService.createProduct(user, productRequestDto, brandId, imageFile);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody ProductRequestDto productRequestDto,
                                                            @RequestPart(required = false) MultipartFile imageFile,
                                                            @PathVariable Long productId) {
        User user = UserUtils.getUser(userDetails);
        ProductResponseDto productResponseDto = productService.updateProduct(user, productRequestDto, productId, imageFile);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<FindProductResponseDto> findProduct(@PathVariable Long productId) {
        FindProductResponseDto productResponseDto = productService.findProduct(productId);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping("brands/{brandId}")
    public ResponseEntity<Page<ProductResponseDto>> findAllProductByBrandId(@PathVariable Long brandId,
                                                                            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponseDto> productResponseDto = productService.findAllProductByBrandId(brandId, pageable);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> findAllProductList(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponseDto> productResponseDto = productService.findAllProduct(pageable);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long productId) {
        User user = UserUtils.getUser(userDetails);
        productService.deleteProduct(productId, user);
        return ResponseEntity.noContent().build();
    }
}

package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.dto.FindProductResponseDto;
import com.Soo_Shinsa.product.dto.ProductRequestDto;
import com.Soo_Shinsa.product.dto.ProductResponseDto;
import com.Soo_Shinsa.product.dto.ProductUpdateDto;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
                                                            @Valid @RequestPart ProductRequestDto productRequestDto,
                                                            @RequestPart(required = false) MultipartFile imageFile,
                                                            @PathVariable Long brandId) {

        User user = UserUtils.getUser(userDetails);
        ProductResponseDto product = productService.createProduct(user, productRequestDto, brandId, imageFile);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductUpdateDto> updateProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestPart ProductUpdateDto productUpdateDto,
                                                          @RequestPart(required = false) MultipartFile imageFile,
                                                          @PathVariable Long productId) {
        User user = UserUtils.getUser(userDetails);
        ProductUpdateDto productResponseDto = productService.updateProduct(user, productUpdateDto, productId, imageFile);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<FindProductResponseDto> findProduct(@PathVariable Long productId) {
        FindProductResponseDto productResponseDto = productService.findProduct(productId);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<Page<ProductResponseDto>> findAllProductList(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @PathVariable Long brandId) {
        Page<ProductResponseDto> productResponseDto = productService.findAllProduct(brandId, page, size);
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

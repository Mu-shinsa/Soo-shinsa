package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.*;
import com.Soo_Shinsa.service.ProductService;
import com.Soo_Shinsa.service.UserService;
import com.Soo_Shinsa.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    @PostMapping("/brands/{brandId}")
    public ResponseEntity<ProductResponseDto> createProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody ProductRequestDto productRequestDto,
                                                            @PathVariable Long brandId) {
        ProductResponseDto productResponseDto = productService.createProduct(UserUtils.getUser(userDetails),productRequestDto,brandId);
        return ResponseEntity.ok(productResponseDto);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody ProductRequestDto productRequestDto,
                                                            @PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.updateProduct(UserUtils.getUser(userDetails),productRequestDto,productId);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> findProduct(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.findProduct(productId);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping("brands/{brandId}")
    public ResponseEntity<List<ProductResponseDto>> findProductListByBrandId(@PathVariable Long brandId) {
        List<ProductResponseDto> productResponseDto = productService.findProductListByBrandId(brandId);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAllProductList() {
        List<ProductResponseDto> productResponseDto = productService.findAllProduct();
        return ResponseEntity.ok(productResponseDto);
    }
}

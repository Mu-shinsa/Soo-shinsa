package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.dto.ProductOptionResponseDto;
import com.Soo_Shinsa.dto.ProductRequestDto;
import com.Soo_Shinsa.dto.ProductResponseDto;
import com.Soo_Shinsa.service.ProductOptionService;
import com.Soo_Shinsa.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/options")
public class ProductOptionController {

    ProductOptionService productOptionService;

    @PostMapping("/products/{productId}")
    public ResponseEntity<ProductOptionResponseDto> createOption(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody ProductOptionRequestDto productOptionRequestDto,
                                                            @PathVariable Long productId) {
        ProductOptionResponseDto productOptionResponseDto = productOptionService.createOption(UserUtils.getUser(userDetails),productOptionRequestDto,productId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @PatchMapping("/{productOptionId}")
    public ResponseEntity<ProductOptionResponseDto> updateOption(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody ProductOptionRequestDto productOptionRequestDto,
                                                            @PathVariable Long productOptionId) {
        ProductOptionResponseDto productOptionResponseDto = productOptionService.updateOption(UserUtils.getUser(userDetails),productOptionRequestDto,productOptionId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @GetMapping("/{productOptionId}")
    public ResponseEntity<ProductOptionResponseDto> findOption(@PathVariable Long productOptionId) {
        ProductOptionResponseDto productOptionResponseDto = productOptionService.findOption(productOptionId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @GetMapping("products/{productId}")
    public ResponseEntity<List<ProductOptionResponseDto>> findOptionListByProductId(@PathVariable Long productId) {
        List<ProductOptionResponseDto> productOptionResponseDto = productOptionService.findOptionListByProductId(productId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

}

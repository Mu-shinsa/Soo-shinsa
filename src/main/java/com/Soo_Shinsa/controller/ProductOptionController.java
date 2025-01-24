package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.product.ProductOptionRequestDto;
import com.Soo_Shinsa.dto.product.ProductOptionResponseDto;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.service.ProductOptionService;
import com.Soo_Shinsa.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/options")
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PostMapping("/products/{productId}")
    public ResponseEntity<ProductOptionResponseDto> createOption(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @RequestBody ProductOptionRequestDto productOptionRequestDto,
                                                                 @PathVariable Long productId) {
        User user = UserUtils.getUser(userDetails);
        ProductOptionResponseDto productOptionResponseDto = productOptionService.createOption(user, productOptionRequestDto, productId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @PatchMapping("/{productOptionId}")
    public ResponseEntity<ProductOptionResponseDto> updateOption(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @RequestBody ProductOptionRequestDto productOptionRequestDto,
                                                                 @PathVariable Long productOptionId) {
        User user = UserUtils.getUser(userDetails);
        ProductOptionResponseDto productOptionResponseDto = productOptionService.updateOption(user, productOptionRequestDto, productOptionId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @GetMapping("/{productOptionId}")
    public ResponseEntity<ProductOptionResponseDto> findOption(@PathVariable Long productOptionId) {
        ProductOptionResponseDto productOptionResponseDto = productOptionService.findOption(productOptionId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductOptionResponseDto>> findOptionListByProductId(@RequestBody ProductOptionRequestDto requestDto,
                                                                                    @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductOptionResponseDto> productOptionResponseDto = productOptionService.findProductsByOptionalSizeAndColor(requestDto, pageable);
        return ResponseEntity.ok(productOptionResponseDto);
    }

}

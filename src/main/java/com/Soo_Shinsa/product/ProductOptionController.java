package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.dto.FindProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
<<<<<<< HEAD
import com.Soo_Shinsa.utils.user.model.User;
=======
import com.Soo_Shinsa.product.dto.ProductOptionUpdateDto;
import com.Soo_Shinsa.user.model.User;
>>>>>>> 4b39b3825ec2c4739765ba1c6974be187a12dc07
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
                                                                 @Valid @RequestBody ProductOptionRequestDto productOptionRequestDto,
                                                                 @PathVariable Long productId) {
        User user = UserUtils.getUser(userDetails);
        ProductOptionResponseDto productOptionResponseDto = productOptionService.createOption(user, productOptionRequestDto, productId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @PatchMapping("/{productOptionId}")
    public ResponseEntity<ProductOptionResponseDto> updateOption(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @RequestBody ProductOptionUpdateDto updateDto,
                                                                 @PathVariable Long productOptionId) {
        User user = UserUtils.getUser(userDetails);
        ProductOptionResponseDto productOptionResponseDto = productOptionService.updateOption(user, updateDto, productOptionId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @GetMapping("/{productOptionId}")
    public ResponseEntity<ProductOptionResponseDto> findOption(@PathVariable Long productOptionId) {
        ProductOptionResponseDto productOptionResponseDto = productOptionService.findOption(productOptionId);
        return ResponseEntity.ok(productOptionResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductOptionResponseDto>> findOptionListByProductId(@RequestBody FindProductOptionRequestDto requestDto,
                                                                                    @RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "10") int size) {
        Page<ProductOptionResponseDto> productOptionResponseDto = productOptionService.findProductsByOptionalSizeAndColor(requestDto, page, size);
        return ResponseEntity.ok(productOptionResponseDto);
    }

}

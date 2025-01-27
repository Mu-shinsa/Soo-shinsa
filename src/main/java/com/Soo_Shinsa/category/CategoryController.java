package com.Soo_Shinsa.category;

import com.Soo_Shinsa.category.dto.CategoryRequestDto;
import com.Soo_Shinsa.category.dto.CategoryResponseDto;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("brands/{brandId}")
    public ResponseEntity<CategoryResponseDto> create(
            @PathVariable Long brandId,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CategoryRequestDto dto
    ) {
        User user = UserUtils.getUser(userDetails);
        CategoryResponseDto saved = categoryService.create(brandId, user, dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}

package com.Soo_Shinsa.category;

import com.Soo_Shinsa.category.dto.CategoryRequestDto;
import com.Soo_Shinsa.category.dto.CategoryResponseDto;
import com.Soo_Shinsa.category.dto.CategoryUpdateRequestDto;
import com.Soo_Shinsa.category.dto.FindCategoryResponseDto;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<CategoryResponseDto> create(@AuthenticationPrincipal UserDetails userDetails,
                                                      @Valid @RequestBody CategoryRequestDto dto,
                                                      @PathVariable Long brandId
    ) {
        User user = UserUtils.getUser(userDetails);
        CategoryResponseDto saved = categoryService.create(brandId, user, dto);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long categoryId) {
        CategoryResponseDto findCategory = categoryService.findById(categoryId);
        return new ResponseEntity<>(findCategory, HttpStatus.OK);
    }

    @GetMapping("brands/{brandId}")
    public ResponseEntity<Page<FindCategoryResponseDto>> findByBrandId(@PathVariable Long brandId,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Page<FindCategoryResponseDto> findCategoryByBrandId = categoryService.findByBrandId(brandId, page, size);
        return new ResponseEntity<>(findCategoryByBrandId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<FindCategoryResponseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Page<FindCategoryResponseDto> findAll = categoryService.findAll(page, size);
        return ResponseEntity.ok(findAll);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> update(@AuthenticationPrincipal UserDetails userDetails,
                                                      @Valid @RequestBody CategoryUpdateRequestDto dto,
                                                      @PathVariable Long categoryId
    ) {
        User user = UserUtils.getUser(userDetails);
        CategoryResponseDto update = categoryService.update(user, dto, categoryId);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}

package com.Soo_Shinsa.brand.controller;

import com.Soo_Shinsa.brand.dto.*;
import com.Soo_Shinsa.brand.service.BrandService;
import com.Soo_Shinsa.utils.CommonResponse;
import com.Soo_Shinsa.utils.ResponseMessage;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<CommonResponse<BrandResponseDto>> createBrand(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody BrandRequestDto brandRequestDto
    ) {
        BrandResponseDto brandResponseDto = brandService.create(UserUtils.getUser(userDetails), brandRequestDto);
        CommonResponse<BrandResponseDto> response = new CommonResponse<>(ResponseMessage.BRAND_CREATE_SUCCESS, brandResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{brandId}")
    public ResponseEntity<CommonResponse<BrandUpdateResponseDto>> updateBrand(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody BrandUpdateRequestDto brandRequestDto,
            @PathVariable Long brandId
    ) {
        BrandUpdateResponseDto brandRefuseResponseDto = brandService.update(
                UserUtils.getUser(userDetails),
                brandRequestDto,
                brandId);
        CommonResponse<BrandUpdateResponseDto> response = new CommonResponse<>(ResponseMessage.BRAND_UPDATE_SUCCESS, brandRefuseResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<CommonResponse<BrandResponseDto>> getBrand(@PathVariable Long brandId) {
        BrandResponseDto findBrand = brandService.findBrandById(brandId);
        CommonResponse<BrandResponseDto> response = new CommonResponse<>(ResponseMessage.BRAND_SELECT_SUCCESS, findBrand);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/owners")
    public ResponseEntity<CommonResponse<List<BrandResponseDto>>> getAllBrandByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        List<BrandResponseDto> getAllBrand = brandService.getAllByUserId(UserUtils.getUser(userDetails));
        CommonResponse<List<BrandResponseDto>> response = new CommonResponse<>(ResponseMessage.BRAND_SELECT_SUCCESS, getAllBrand);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<Page<FindBrandAllResponseDto>>> getAllBrands(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<FindBrandAllResponseDto> getAllBrand = brandService.getAll(page, size);
        CommonResponse<Page<FindBrandAllResponseDto>> response = new CommonResponse<>(ResponseMessage.BRAND_SELECT_SUCCESS, getAllBrand);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

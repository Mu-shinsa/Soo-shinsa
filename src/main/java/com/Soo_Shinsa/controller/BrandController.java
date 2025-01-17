package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.BrandUpdateResponseDto;
import com.Soo_Shinsa.dto.BrandRequestDto;
import com.Soo_Shinsa.dto.BrandResponseDto;
import com.Soo_Shinsa.service.BrandService;
import com.Soo_Shinsa.service.UserService;
import lombok.RequiredArgsConstructor;
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

    private final UserService userService;
    private final BrandService brandService;

    @PostMapping("/{userId}")
    public ResponseEntity<BrandResponseDto> createBrand(
            @RequestBody BrandRequestDto brandRequestDto,
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        BrandResponseDto brandResponseDto = brandService.create(
                brandRequestDto.getRegistrationNum(),
                brandRequestDto.getName(),
                brandRequestDto.getContext(),
                userId,
                userDetails.getAuthorities());
        return new ResponseEntity<>(brandResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{brandId}/admin/{userId}")
    public ResponseEntity<BrandUpdateResponseDto> updateBrand(
            @RequestBody BrandRequestDto brandRequestDto,
            @PathVariable Long brandId,
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        BrandUpdateResponseDto brandRefuseResponseDto = brandService.update(
                brandRequestDto.getStatus(),
                brandRequestDto.getContext(),
                brandId,
                userId,
                userDetails.getAuthorities());
        return new ResponseEntity<>(brandRefuseResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{brandId}/owners/{userId}")
    public ResponseEntity<BrandResponseDto> getBrand(
            @PathVariable Long brandId,
            @PathVariable Long userId
    ) {
        BrandResponseDto findBrand = brandService.findBrandById(brandId, userId);
        return new ResponseEntity<>(findBrand, HttpStatus.OK);
    }

    @GetMapping("/owners/{userId}")
    public ResponseEntity<List<BrandResponseDto>> getAllBrandByUserId(
            @PathVariable Long userId
    ) {
        List<BrandResponseDto> getAllBrand = brandService.getAllByUserId(userId);
        return new ResponseEntity<>(getAllBrand, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<BrandResponseDto>> getAllBrands() {
        List<BrandResponseDto> getAllBrand = brandService.getAll();
        return new ResponseEntity<>(getAllBrand, HttpStatus.OK);
    }
}

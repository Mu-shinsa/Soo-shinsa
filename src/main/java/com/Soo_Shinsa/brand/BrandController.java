package com.Soo_Shinsa.brand;

import com.Soo_Shinsa.brand.dto.BrandUpdateResponseDto;
import com.Soo_Shinsa.brand.dto.BrandRequestDto;
import com.Soo_Shinsa.brand.dto.BrandResponseDto;
import com.Soo_Shinsa.user.UserService;
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
@RequestMapping("/brands")
public class BrandController {

    private final UserService userService;
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponseDto> createBrand(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BrandRequestDto brandRequestDto
    ) {
        BrandResponseDto brandResponseDto = brandService.create(UserUtils.getUser(userDetails),brandRequestDto);
        return ResponseEntity.ok(brandResponseDto);
    }

    @PatchMapping("/{brandId}")
    public ResponseEntity<BrandUpdateResponseDto> updateBrand(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BrandRequestDto brandRequestDto,
            @PathVariable Long brandId
    ) {
        BrandUpdateResponseDto brandRefuseResponseDto = brandService.update(
                UserUtils.getUser(userDetails),
                brandRequestDto,
                brandId);
        return new ResponseEntity<>(brandRefuseResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandResponseDto> getBrand(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long brandId
    ) {
        BrandResponseDto findBrand = brandService.findBrandById(UserUtils.getUser(userDetails),brandId);
        return new ResponseEntity<>(findBrand, HttpStatus.OK);
    }

    @GetMapping("/owners")
    public ResponseEntity<List<BrandResponseDto>> getAllBrandByUserId(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<BrandResponseDto> getAllBrand = brandService.getAllByUserId(UserUtils.getUser(userDetails));
        return new ResponseEntity<>(getAllBrand, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<BrandResponseDto>> getAllBrands(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<BrandResponseDto> getAllBrand = brandService.getAll(UserUtils.getUser(userDetails));
        return new ResponseEntity<>(getAllBrand, HttpStatus.OK);
    }
}

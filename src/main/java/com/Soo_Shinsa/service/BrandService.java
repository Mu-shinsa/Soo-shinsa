package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.brand.BrandRequestDto;
import com.Soo_Shinsa.dto.brand.BrandUpdateResponseDto;
import com.Soo_Shinsa.dto.brand.BrandResponseDto;
import com.Soo_Shinsa.model.Brand;
import com.Soo_Shinsa.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface BrandService {


    BrandResponseDto create(User user, BrandRequestDto brandRequestDto);

    BrandUpdateResponseDto update(User user, BrandRequestDto brandRequestDto, Long id);

    Brand findByIdOrElseThrow(Long id);

    BrandResponseDto findBrandById(Long brandId, Long userId);

    List<BrandResponseDto> getAllByUserId(Long userId);

    List<BrandResponseDto> getAll();
}

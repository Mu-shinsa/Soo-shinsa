package com.Soo_Shinsa.service;

import com.Soo_Shinsa.dto.brand.BrandUpdateResponseDto;
import com.Soo_Shinsa.dto.brand.BrandResponseDto;
import com.Soo_Shinsa.model.Brand;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface BrandService {


    BrandResponseDto create(String registrationNum, String name, String context, Long userId, Collection<? extends GrantedAuthority> role);

    BrandUpdateResponseDto update(String status, String context, Long brandId, Long userId, Collection<? extends GrantedAuthority> authorities);

    Brand findByIdOrElseThrow(Long id);

    BrandResponseDto findBrandById(Long brandId, Long userId);

    List<BrandResponseDto> getAllByUserId(Long userId);

    List<BrandResponseDto> getAll();
}

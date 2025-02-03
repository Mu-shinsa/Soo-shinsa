package com.Soo_Shinsa.brand;

import com.Soo_Shinsa.brand.dto.BrandRequestDto;
import com.Soo_Shinsa.brand.dto.BrandResponseDto;
import com.Soo_Shinsa.brand.dto.BrandUpdateResponseDto;
import com.Soo_Shinsa.user.model.User;

import java.util.List;

public interface BrandService {

    BrandResponseDto create(User user, BrandRequestDto brandRequestDto);

    BrandUpdateResponseDto update(User user, BrandRequestDto brandRequestDto, Long id);

    BrandResponseDto findBrandById(User user,Long brandId);

    List<BrandResponseDto> getAllByUserId(User user);

    List<BrandResponseDto> getAll(User user);
}

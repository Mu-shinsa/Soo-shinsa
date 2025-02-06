package com.Soo_Shinsa.brand.service;

import com.Soo_Shinsa.brand.dto.*;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {

    BrandResponseDto create(User user, BrandRequestDto brandRequestDto);

    BrandUpdateResponseDto update(User user, BrandUpdateRequestDto dto, Long brandId);

    BrandResponseDto findBrandById(Long brandId);

    List<BrandResponseDto> getAllByUserId(User user);

    Page<FindBrandAllResponseDto> getAll(int page, int size);
}

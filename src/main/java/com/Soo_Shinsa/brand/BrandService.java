package com.Soo_Shinsa.brand;

import com.Soo_Shinsa.brand.dto.BrandRequestDto;
import com.Soo_Shinsa.brand.dto.BrandResponseDto;
import com.Soo_Shinsa.brand.dto.BrandUpdateResponseDto;
import com.Soo_Shinsa.brand.dto.FindBrandAllResponseDto;
import com.Soo_Shinsa.user.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {

    BrandResponseDto create(User user, BrandRequestDto brandRequestDto);

    BrandUpdateResponseDto update(User user, BrandRequestDto brandRequestDto, Long id);

    BrandResponseDto findBrandById(Long brandId);

    List<BrandResponseDto> getAllByUserId(User user);

    Page<FindBrandAllResponseDto> getAll(int page, int size);
}

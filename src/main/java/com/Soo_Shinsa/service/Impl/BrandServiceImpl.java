package com.Soo_Shinsa.service.Impl;


import com.Soo_Shinsa.constant.BrandStatus;
import com.Soo_Shinsa.dto.brand.BrandRequestDto;
import com.Soo_Shinsa.dto.brand.BrandUpdateResponseDto;
import com.Soo_Shinsa.dto.brand.BrandResponseDto;
import com.Soo_Shinsa.model.Brand;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.BrandRepository;
import com.Soo_Shinsa.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    @Override
    public BrandResponseDto create(User user, BrandRequestDto dto) {

        Brand savedBrand = new Brand(
                dto.getRegistrationNum(),
                dto.getName(),
                dto.getContext(),
                BrandStatus.APPLY,
                user
        );

        return BrandResponseDto.toDto(savedBrand);
    }

    @Transactional
    @Override
    public BrandUpdateResponseDto update(User user, BrandRequestDto dto, Long brandId) {

        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);
        findBrand.update(dto.getRegistrationNum(),dto.getName(),dto.getContext(), dto.getStatus());
        Brand saved = brandRepository.save(findBrand);

        return BrandUpdateResponseDto.toDto(saved);
    }

    @Override
    public BrandResponseDto findBrandById(User user, Long brandId) {

        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);

        return BrandResponseDto.toDto(findBrand);
    }


    @Override
    public List<BrandResponseDto> getAllByUserId(User user) {

        List<Brand> brands = brandRepository.findAllByUserUserId(user.getUserId());

        return brands.stream().map(BrandResponseDto::toDto).toList();
    }


    @Override
    public List<BrandResponseDto> getAll(User user) {

        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(BrandResponseDto::toDto).toList();
    }

}

package com.Soo_Shinsa.brand.service;

import com.Soo_Shinsa.brand.dto.*;
import com.Soo_Shinsa.brand.model.Brand;
import com.Soo_Shinsa.brand.repository.BrandRepository;
import com.Soo_Shinsa.constant.BrandStatus;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        EntityValidator.validateAdminOrVendorAccess(user);
        Brand savedBrand = Brand.builder()
                .registrationNum(dto.getRegistrationNum())
                .name(dto.getName())
                .context(dto.getContext())
                .status(BrandStatus.APPLY)
                .user(user)
                .build();

        brandRepository.save(savedBrand);

        return BrandResponseDto.toDto(savedBrand);
    }

    @Transactional
    @Override
    public BrandUpdateResponseDto update(User user, BrandUpdateRequestDto dto, Long brandId) {

        EntityValidator.validateAdminOrVendorAccess(user);
        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);
        findBrand.update(dto.getRegistrationNum(), dto.getName(), dto.getContext(), dto.getStatus());

        return BrandUpdateResponseDto.toDto(findBrand);
    }

    @Override
    public BrandResponseDto findBrandById(Long brandId) {

        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);

        return BrandResponseDto.toDto(findBrand);
    }


    @Override
    public List<BrandResponseDto> getAllByUserId(User user) {

        List<Brand> brands = brandRepository.findAllByUserUserId(user.getUserId());

        return brands.stream().map(BrandResponseDto::toDto).toList();
    }


    @Override
    public Page<FindBrandAllResponseDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return brandRepository.getAllBrand(pageable);
    }

}

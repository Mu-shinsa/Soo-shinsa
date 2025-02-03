package com.Soo_Shinsa.brand;

import com.Soo_Shinsa.brand.dto.BrandRequestDto;
import com.Soo_Shinsa.brand.dto.BrandResponseDto;
import com.Soo_Shinsa.brand.dto.BrandUpdateResponseDto;
import com.Soo_Shinsa.constant.BrandStatus;
import com.Soo_Shinsa.user.model.User;
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
    public BrandUpdateResponseDto update(User user, BrandRequestDto dto, Long brandId) {

        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);
        findBrand.update(dto.getRegistrationNum(),dto.getName(),dto.getContext(), dto.getStatus());

        return BrandUpdateResponseDto.toDto(findBrand);
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

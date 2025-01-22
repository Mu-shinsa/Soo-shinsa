package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.constant.BrandStatus;
import com.Soo_Shinsa.dto.brand.BrandRequestDto;
import com.Soo_Shinsa.dto.brand.BrandUpdateResponseDto;
import com.Soo_Shinsa.dto.brand.BrandResponseDto;
import com.Soo_Shinsa.model.Brand;
import com.Soo_Shinsa.model.Review;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.BrandRepository;
import com.Soo_Shinsa.repository.UserRepository;
import com.Soo_Shinsa.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final UserRepository userRepository;

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

        Brand findBrand = findByIdOrElseThrow(brandId);
        findBrand.update(dto.getRegistrationNum(),dto.getName(),dto.getContext(), dto.getStatus());
        Brand saved = brandRepository.save(findBrand);

        return BrandUpdateResponseDto.toDto(saved);
    }

    @Transactional
    @Override
    public BrandResponseDto findBrandById(Long brandId, Long userId) {

        checkUser(userId);
        Brand findBrand = findByIdOrElseThrow(brandId);

        return BrandResponseDto.toDto(findBrand);
    }

    @Transactional
    @Override
    public List<BrandResponseDto> getAllByUserId(Long userId) {

        User user = checkUser(userId);

        List<Brand> brands = brandRepository.findAllByUserUserId(user.getUserId());

        return brands.stream().map(BrandResponseDto::toDto).toList();
    }

    @Transactional
    @Override
    public List<BrandResponseDto> getAll() {

        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(BrandResponseDto::toDto).toList();
    }


    @Transactional(readOnly = true)
    @Override
    public Brand findByIdOrElseThrow(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}

package com.Soo_Shinsa.brand;

import com.Soo_Shinsa.brand.dto.BrandRequestDto;
import com.Soo_Shinsa.brand.dto.BrandResponseDto;
import com.Soo_Shinsa.brand.dto.BrandUpdateResponseDto;
import com.Soo_Shinsa.brand.dto.FindBrandAllResponseDto;
import com.Soo_Shinsa.constant.BrandStatus;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.utils.EntityValidator;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final JPAQueryFactory queryFactory;

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
    public BrandUpdateResponseDto update(User user, BrandRequestDto dto, Long brandId) {

        EntityValidator.validateAdminOrVendorAccess(user);
        Brand findBrand = brandRepository.findByIdOrElseThrow(brandId);
        findBrand.update(dto.getRegistrationNum(),dto.getName(),dto.getContext(), dto.getStatus());

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
        QBrand brand = QBrand.brand;
        Pageable pageable = PageRequest.of(page, size);

        // 동적 조건 생성
        BooleanBuilder builder = new BooleanBuilder();

        // QueryDSL로 브랜드 목록 조회
        List<Brand> brands = queryFactory.selectFrom(brand)
                .where(builder)
                .orderBy(brand.name.asc())
                .fetch();

        // 총 개수 조회
        long total = queryFactory.select(brand.count())
                .from(brand)
                .where(builder)
                .fetch()
                .size();

        List<FindBrandAllResponseDto> findBrandAllDtos = brands.stream()
                .map(FindBrandAllResponseDto::of)  // 반환 타입에 맞는 DTO로 변환
                .toList();

        return new PageImpl<>(findBrandAllDtos, pageable, total);
    }

}

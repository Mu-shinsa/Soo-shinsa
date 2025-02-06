package com.Soo_Shinsa.coupon.dto;

import com.Soo_Shinsa.coupon.model.CouponBrandRelation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CouponBrandRelationDto {
    private Long brandId;

    @Builder
    public CouponBrandRelationDto(Long brandId) {
        this.brandId = brandId;
    }

    public static List<CouponBrandRelationDto> toDtos(List<CouponBrandRelation> relations) {
        return relations.stream()
                .map(relation -> CouponBrandRelationDto.builder()
                        .brandId(relation.getBrand().getId())
                        .build())
                .collect(Collectors.toList());
    }
}

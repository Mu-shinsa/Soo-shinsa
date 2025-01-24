package com.Soo_Shinsa.dto.product;

import com.Soo_Shinsa.model.Product;
import com.Soo_Shinsa.model.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class FindProductResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<ProductOptionResponseDto> options;

    public FindProductResponseDto(Long id, String name, BigDecimal price, List<ProductOptionResponseDto> options) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.options = options;
    }

    public static FindProductResponseDto toDto(Product product, List<ProductOption> productOptions) {
        List<ProductOptionResponseDto> optionDtos = productOptions.stream()
                .map(ProductOptionResponseDto::toDto)
                .toList();

        return new FindProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                optionDtos
        );
    }
}

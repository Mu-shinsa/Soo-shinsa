package com.Soo_Shinsa.product;

import com.Soo_Shinsa.product.dto.FindProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionRequestDto;
import com.Soo_Shinsa.product.dto.ProductOptionResponseDto;
<<<<<<< HEAD
import com.Soo_Shinsa.utils.user.model.User;
=======
import com.Soo_Shinsa.product.dto.ProductOptionUpdateDto;
import com.Soo_Shinsa.user.model.User;
>>>>>>> 4b39b3825ec2c4739765ba1c6974be187a12dc07
import org.springframework.data.domain.Page;

public interface ProductOptionService {
    ProductOptionResponseDto createOption(User user, ProductOptionRequestDto productOptionRequestDto, Long productId);

    ProductOptionResponseDto updateOption(User user, ProductOptionUpdateDto dto, Long productId);

    ProductOptionResponseDto findOption(Long productOptionId);

    Page<ProductOptionResponseDto> findProductsByOptionalSizeAndColor(FindProductOptionRequestDto requestDto, int page, int size);
}
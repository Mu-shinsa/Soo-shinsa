package com.Soo_Shinsa.review;

import com.Soo_Shinsa.constant.TargetType;
import com.Soo_Shinsa.image.Image;
import com.Soo_Shinsa.image.ImageService;
import com.Soo_Shinsa.order.OrderItemRepository;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.product.ProductRepository;
import com.Soo_Shinsa.product.model.Product;
import com.Soo_Shinsa.review.dto.ReviewRateDto;
import com.Soo_Shinsa.review.dto.ReviewRequestDto;
import com.Soo_Shinsa.review.dto.ReviewResponseDto;
import com.Soo_Shinsa.review.dto.ReviewUpdateDto;
import com.Soo_Shinsa.review.model.QReview;
import com.Soo_Shinsa.review.model.Review;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final ImageService imageService;
    private final ProductRepository productRepository;
    private final JPAQueryFactory queryFactory;

    /**
     * 리뷰 생성
     *
     * @param orderItemId
     * @param requestDto
     * @return ReviewResponseDto.toDto(saveReview)
     */
    @Transactional
    @Override
    public ReviewResponseDto createReview(Long orderItemId, ReviewRequestDto requestDto, User user, MultipartFile imageFile) {
        OrderItem orderItem = orderItemRepository.findByIdOrElseThrow(orderItemId);

        EntityValidator.validateUserOwnership(user.getUserId(), orderItem.getOrder().getUser().getUserId());

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            Image uploaded = imageService.uploadImage(imageFile, TargetType.REVIEW, orderItemId);
            imageUrl = uploaded.getPath();
        }

        Review review = Review.builder()
                .rate(requestDto.getRate())
                .content(requestDto.getContent())
                .product(orderItem.getProduct())
                .user(user)
                .orderItem(orderItem)
                .imageUrl(imageUrl)
                .build();

        Review saveReview = reviewRepository.save(review);

        return ReviewResponseDto.toDto(saveReview);
    }

    /**
     * 리뷰 조회
     *
     * @param reviewId
     * @return ReviewResponseDto.toDto(review)
     */
    @Transactional(readOnly = true)
    @Override
    public ReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findByIdOrElseThrow(reviewId);

        return ReviewResponseDto.toDto(review);
    }

    /**
     * 리뷰 수정
     *
     * @param reviewId
     * @param updateDto
     * @return updateDto
     */
    @Transactional
    @Override
    public ReviewUpdateDto updateReview(Long reviewId, ReviewUpdateDto updateDto, User user, MultipartFile imageFile) {
        Review review = reviewRepository.findByIdOrElseThrow(reviewId);

        EntityValidator.validateUserOwnership(user.getUserId(), review.getUser().getUserId());

        String newImageUrl = review.getImageUrl(); // 기존 이미지 URL 유지
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지 삭제 후 새로운 이미지 업로드
            Image updatedImage = imageService.updateImage(imageFile, review.getImageUrl(), TargetType.REVIEW);
            newImageUrl = updatedImage.getPath();
        }

        review.update(updateDto.getRate(), updateDto.getContent(), newImageUrl);

        return ReviewUpdateDto.toDto(review);
    }

    /**
     * 상품 리뷰 조회 (별점 순)
     *
     * @param productId
     * @param productId 상품 ID
     * @param reviewRateDto 리뷰 별점 필터링 DTO
     * @param page      페이지 번호
     * @param size      페이지 크기
     * @return reviews.map(ReviewResponseDto : : toDto);
     */
    public Page<ReviewResponseDto> getReviewsByProductId(Long productId, ReviewRateDto reviewRateDto, int page, int size) {
        // Pageable 객체를 Service에서 생성
        Product product = productRepository.findByIdOrElseThrow(productId);
        QReview review = QReview.review;
        Pageable pageable = PageRequest.of(page, size);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(review.product.id.eq(productId));

        if (reviewRateDto.getMinRate() != null) {
            builder.and(review.rate.goe(reviewRateDto.getMinRate()));
        }

        if (reviewRateDto.getMaxRate() != null) {
            builder.and(review.rate.loe(reviewRateDto.getMaxRate()));
        }

        List<Review> reviews = queryFactory
                .selectFrom(review)
                .where(builder)
                .orderBy(review.rate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(review)
                .where(review.product.id.eq(productId))
                .fetch()
                .size();

        List<ReviewResponseDto> reviewResponseDtos = reviews.stream()
                .map(ReviewResponseDto::toDto)
                .toList();

        return new PageImpl<>(reviewResponseDtos, pageable, total);
    }


    /**
     * 리뷰 삭제
     *
     * @param reviewId
     */
    @Transactional
    @Override
    public void delete(Long reviewId, User user) {
        Review review = reviewRepository.findByIdOrElseThrow(reviewId);

        EntityValidator.validateUserOwnership(user.getUserId(), review.getUser().getUserId());

        if (review.getImageUrl() != null) {
            imageService.deleteImage(review.getImageUrl()); // URL을 사용해 이미지 삭제
        }

        reviewRepository.delete(review);
    }
}

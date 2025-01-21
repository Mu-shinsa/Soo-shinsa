package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.dto.ReviewRequestDto;
import com.Soo_Shinsa.dto.ReviewResponseDto;
import com.Soo_Shinsa.dto.ReviewUpdateDto;
import com.Soo_Shinsa.entity.OrderItem;
import com.Soo_Shinsa.entity.Review;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.*;
import com.Soo_Shinsa.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    /**
     * 리뷰 생성
     * @param orderItemId
     * @param requestDto
     * @return ReviewResponseDto.toDto(saveReview)
     */
    @Transactional
    @Override
    public ReviewResponseDto createReview (Long orderItemId, ReviewRequestDto requestDto, User user) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 항목입니다."));

        Review review = Review.builder()
                .rate(requestDto.getRate())
                .content(requestDto.getContent())
                .product(orderItem.getProduct())
                .user(user)
                .orderItem(orderItem)
                .build();

        Review saveReview = reviewRepository.save(review);

        return ReviewResponseDto.toDto(saveReview);
    }

    /**
     * 리뷰 조회
     * @param reviewId
     * @return ReviewResponseDto.toDto(review)
     */
    @Transactional(readOnly = true)
    @Override
    public ReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        return ReviewResponseDto.toDto(review);
    }

    /**
     * 리뷰 수정
     * @param reviewId
     * @param updateDto
     * @return updateDto
     */
    @Transactional
    @Override
    public ReviewUpdateDto updateReview(Long reviewId, ReviewUpdateDto updateDto, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        if (!review.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("본인의 리뷰만 수정할 수 있습니다.");
        }

        review.update(updateDto.getRate(), updateDto.getContent());
        Review saveReview = reviewRepository.save(review);

        return ReviewUpdateDto.toDto(saveReview);
    }

    /**
     * 상품 리뷰 조회
     * @param productId
     * @param productId 상품 ID
     * @param pageable  페이징 및 정렬 정보
     * @return reviews.map(ReviewResponseDto::toDto);
     */
    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> getAllReviewProduct(Long productId, Pageable pageable) {
        // 상품 ID를 기반으로 리뷰 조회 (페이징 적용)
        Page<Review> reviews = reviewRepository.findAllByProductId(productId, pageable);

        // Review -> ReviewResponseDto 변환
        return reviews.map(ReviewResponseDto::toDto);
    }


    /**
     * 리뷰 삭제
     * @param reviewId
     */
    @Transactional
    @Override
    public void delete(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        validateUser(user, review);

        reviewRepository.delete(review);
    }

    private static void validateUser(User user, Review review) {
        if (!review.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("리뷰를 삭제할 권한이 없습니다.");
        }
    }
}

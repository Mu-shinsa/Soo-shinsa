package com.Soo_Shinsa.service.Impl;

import com.Soo_Shinsa.dto.review.ReviewRequestDto;
import com.Soo_Shinsa.dto.review.ReviewResponseDto;
import com.Soo_Shinsa.dto.review.ReviewUpdateDto;
import com.Soo_Shinsa.entity.OrderItem;
import com.Soo_Shinsa.entity.Orders;
import com.Soo_Shinsa.entity.Product;
import com.Soo_Shinsa.entity.Review;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.repository.*;
import com.Soo_Shinsa.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ReviewResponseDto createReview (Long orderItemId, ReviewRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

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

    @Transactional(readOnly = true)
    @Override
    public ReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        return ReviewResponseDto.toDto(review);
    }

    @Transactional
    @Override
    public ReviewUpdateDto updateReview(Long reviewId, ReviewUpdateDto updateDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        review.update(updateDto.getRate(), updateDto.getContent());

        return updateDto;
    }

    @Transactional
    @Override
    public void delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        reviewRepository.delete(review);
    }
}

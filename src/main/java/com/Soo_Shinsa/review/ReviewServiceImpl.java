package com.Soo_Shinsa.review;

import com.Soo_Shinsa.review.dto.ReviewRequestDto;
import com.Soo_Shinsa.review.dto.ReviewResponseDto;
import com.Soo_Shinsa.review.dto.ReviewUpdateDto;
import com.Soo_Shinsa.image.Image;
import com.Soo_Shinsa.order.model.OrderItem;
import com.Soo_Shinsa.order.OrderItemRepository;
import com.Soo_Shinsa.review.model.Review;
import com.Soo_Shinsa.user.model.User;
import com.Soo_Shinsa.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final ImageService imageService;

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
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 항목입니다."));

        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            Image uploaded = imageService.uploadImage(imageFile, "reviews");
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
        Review review = reviewRepository.findById(reviewId, "존재하지 않는 리뷰입니다.");

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
        Review review = reviewRepository.findById(reviewId, "존재하지 않는 리뷰입니다.");

        user.validateReviewUser(review);

        String newImageUrl = review.getImageUrl(); // 기존 이미지 URL 유지
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지 삭제 후 새로운 이미지 업로드
            Image updatedImage = imageService.updateImage(imageFile, review.getImageUrl(), "reviews");
            newImageUrl = updatedImage.getPath();
        }

        review.update(updateDto.getRate(), updateDto.getContent(), newImageUrl);

        return ReviewUpdateDto.toDto(review);
    }

    /**
     * 상품 리뷰 조회
     *
     * @param productId
     * @param productId 상품 ID
     * @param page      페이지 번호
     * @param size      페이지 크기
     * @return reviews.map(ReviewResponseDto : : toDto);
     */
    public Page<ReviewResponseDto> getReviewsByProductId(Long productId, int page, int size) {
        // Pageable 객체를 Service에서 생성
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findAllByProductId(productId, pageable);
        return reviews.map(ReviewResponseDto::toDto);
    }


    /**
     * 리뷰 삭제
     *
     * @param reviewId
     */
    @Transactional
    @Override
    public void delete(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId, "존재하지 않는 리뷰입니다.");

        user.validateReviewUser(review);

        if (review.getImageUrl() != null) {
            imageService.deleteImage(review.getImageUrl()); // URL을 사용해 이미지 삭제
        }

        reviewRepository.delete(review);
    }
}

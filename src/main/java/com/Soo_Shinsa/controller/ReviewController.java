package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.auth.UserDetailsImp;
import com.Soo_Shinsa.dto.review.ReviewRequestDto;
import com.Soo_Shinsa.dto.review.ReviewResponseDto;
import com.Soo_Shinsa.dto.review.ReviewUpdateDto;
import com.Soo_Shinsa.model.User;
import com.Soo_Shinsa.service.ReviewService;
import com.Soo_Shinsa.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/order-item/{orderItemId}")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable Long orderItemId,
                                                          @Valid @RequestBody ReviewRequestDto requestDto,
                                                          @RequestPart (required = false) MultipartFile imageFile,
                                                          @AuthenticationPrincipal UserDetailsImp userDetails) {
        User user = UserUtils.getUser(userDetails);
        ReviewResponseDto review = reviewService.createReview(orderItemId, requestDto, user, imageFile);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long reviewId) {

        ReviewResponseDto review = reviewService.getReview(reviewId);
        return ResponseEntity.ok(review);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewUpdateDto> updateReview(@PathVariable Long reviewId,
                                                        @Valid @RequestBody ReviewUpdateDto updateDto,
                                                        @RequestPart(required = false) MultipartFile imageFile,
                                                        @AuthenticationPrincipal UserDetailsImp userDetails) {
        User user = UserUtils.getUser(userDetails);
        ReviewUpdateDto review = reviewService.updateReview(reviewId, updateDto, user, imageFile);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ReviewResponseDto>> getAllReviewByProductId(@PathVariable Long productId,
                                                                           @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ReviewResponseDto> reviews = reviewService.getAllReviewProduct(productId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId,
                                             @AuthenticationPrincipal UserDetailsImp userDetails) {
        User user = UserUtils.getUser(userDetails);
        reviewService.delete(reviewId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


package com.Soo_Shinsa.controller;

import com.Soo_Shinsa.dto.review.ReviewRequestDto;
import com.Soo_Shinsa.dto.review.ReviewResponseDto;
import com.Soo_Shinsa.dto.review.ReviewUpdateDto;
import com.Soo_Shinsa.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/order-item/{orderItemId}")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable Long orderItemId, @RequestBody ReviewRequestDto requestDto) {
        ReviewResponseDto review = reviewService.createReview(orderItemId, requestDto);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(Long reviewId) {
        ReviewResponseDto review = reviewService.getReview(reviewId);
        return ResponseEntity.ok(review);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewUpdateDto> updateReview(Long reviewId, @RequestBody ReviewUpdateDto updateDto) {
        ReviewUpdateDto review = reviewService.updateReview(reviewId, updateDto);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(Long reviewId) {
        reviewService.delete(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

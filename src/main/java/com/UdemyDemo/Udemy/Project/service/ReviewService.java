package com.UdemyDemo.Udemy.Project.service;

import com.UdemyDemo.Udemy.Project.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(Long courseId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByCourseId(Long id);
    ReviewDto getReviewById(Long id, Long courseId);
    ReviewDto updateReview(Long courseId, Long reviewId, ReviewDto reviewDto);
    void deleteReview(Long id, Long courseId);
}

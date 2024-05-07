package com.UdemyDemo.Udemy.Project.service.impl;

import com.UdemyDemo.Udemy.Project.dto.ReviewDto;
import com.UdemyDemo.Udemy.Project.entity.Course;
import com.UdemyDemo.Udemy.Project.entity.Review;
import com.UdemyDemo.Udemy.Project.exception.NotFoundException;
import com.UdemyDemo.Udemy.Project.mapper.ReviewMapper;
import com.UdemyDemo.Udemy.Project.repository.CourseRepository;
import com.UdemyDemo.Udemy.Project.repository.ReviewRepository;
import com.UdemyDemo.Udemy.Project.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final ReviewMapper reviewMapper;


    @Override
    public ReviewDto createReview(Long courseId, ReviewDto reviewDto) {
        Review review = reviewMapper.toEntity(reviewDto);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with associated review not found"));

        review.setCourse(course);
        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toReviewDto(savedReview);
    }

    @Override
    public List<ReviewDto> getReviewsByCourseId(Long id) {
        List<Review> reviews = reviewRepository.findByCourseId(id);
        return reviews.stream().map(reviewMapper::toReviewDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(Long id, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with associated review not found"));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with associated review not found"));

        if (review.getCourse().getId() != course.getId()) {
            throw new NotFoundException("This review does not belong to the course");
        }

        return reviewMapper.toReviewDto(review);
    }

    @Override
    public ReviewDto updateReview(Long courseId, Long reviewId, ReviewDto reviewDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with associated review not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review with associated review not found"));
        if (review.getCourse().getId() != course.getId()) {
            throw new NotFoundException("This review does not belong to the course");
        }
        review.setMessage(reviewDto.getMessage());
        review.setStar(reviewDto.getLike());


        Review updateReview = reviewRepository.save(review);
        return reviewMapper.toReviewDto(updateReview);
    }


    @Override
    public void deleteReview(Long id, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course with associated review not found"));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with associated review not found"));

        if (review.getCourse().getId() != course.getId()) {
            throw new NotFoundException("This review does not belong to the course");
        }

        reviewRepository.delete(review);

    }
}

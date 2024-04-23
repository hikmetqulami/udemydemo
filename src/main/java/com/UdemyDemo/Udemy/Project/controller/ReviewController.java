package com.UdemyDemo.Udemy.Project.controller;

import com.UdemyDemo.Udemy.Project.dto.ReviewDto;
import com.UdemyDemo.Udemy.Project.service.impl.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ReviewController {


    private final ReviewServiceImpl reviewService;


    @PostMapping("/course/{courseId}/reviews")
    public ResponseEntity<ReviewDto> addReview(@PathVariable(value = "courseId") Long courseId,
                                               @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(courseId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("course/{courseId}/reviews")
    public List<ReviewDto> getReviewsByCourseId(@PathVariable(value = "courseId") Long courseId){
    return reviewService.getReviewsByCourseId(courseId);
    }


    @GetMapping("course/{courseId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "courseId") Long id,
                                                   @PathVariable(value = "id") Long reviewId) {

        ReviewDto reviewDto = reviewService.getReviewById(id, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("course/{couseId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "courseId")Long courseId,
                                                  @PathVariable(value = "id") Long id,
                                                  @RequestBody ReviewDto reviewDto) {
        ReviewDto updateReview = reviewService.updateReview(id, courseId, reviewDto);
        return new ResponseEntity<>(updateReview, HttpStatus.OK);
    }

    @DeleteMapping("course/{courseId}/review/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "courseId") Long courseId,
                                               @PathVariable(value = "id") Long reviewId) {
        reviewService.deleteReview(reviewId, courseId);
        return new ResponseEntity<>("Deleted Review", HttpStatus.OK);
    }
}

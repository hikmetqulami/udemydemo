package com.UdemyDemo.Udemy.Project.mapper;

import com.UdemyDemo.Udemy.Project.dto.ReviewDto;
import com.UdemyDemo.Udemy.Project.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto toReviewDto(Review review);

    Review toEntity(ReviewDto reviewDto);
}

package com.UdemyDemo.Udemy.Project.repository;

import com.UdemyDemo.Udemy.Project.entity.ImageModel;
import com.UdemyDemo.Udemy.Project.entity.VideoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoModelRepository extends JpaRepository<VideoModel, Long> {
//    Optional<VideoModel> findByUserId(Long userId);

}

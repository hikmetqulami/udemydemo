package com.UdemyDemo.Udemy.Project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VideoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] video;

}

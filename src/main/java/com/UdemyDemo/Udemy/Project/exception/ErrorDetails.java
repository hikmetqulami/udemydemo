package com.UdemyDemo.Udemy.Project.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private LocalDateTime timeStamp;

    private String message;

    private String details;

    private Integer statusCode;
}

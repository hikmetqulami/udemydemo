package com.UdemyDemo.Udemy.Project.controller;

import com.UdemyDemo.Udemy.Project.entity.ImageModel;
import com.UdemyDemo.Udemy.Project.payload.response.MessageResponse;
import com.UdemyDemo.Udemy.Project.service.impl.ImageServiceIml;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/image")
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceIml imageServiceIml;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {
        imageServiceIml.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
    }

    @PostMapping("/{courseId}/upload")
    public ResponseEntity<MessageResponse>uploadImageToCourse(@PathVariable("courseId") String courseId,
                                                              @RequestParam("file") MultipartFile file,
                                                              Principal principal) throws IOException {

        imageServiceIml.uploadImageToCourse(file, principal, Long.parseLong(courseId));
        return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
    }

    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageForUser(Principal principal) {
        ImageModel userImage = imageServiceIml.getImageToUser(principal);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @GetMapping("/{courseId}/image")
    public ResponseEntity<ImageModel> getImageForCourse(@PathVariable("courseId") String courseId) {
        ImageModel userImage = imageServiceIml.getImageToCourse(Long.parseLong(courseId));
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }
}

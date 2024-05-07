package com.UdemyDemo.Udemy.Project.service.impl;

import com.UdemyDemo.Udemy.Project.entity.Course;
import com.UdemyDemo.Udemy.Project.entity.ImageModel;
import com.UdemyDemo.Udemy.Project.entity.User;
import com.UdemyDemo.Udemy.Project.exception.NotFoundException;
import com.UdemyDemo.Udemy.Project.repository.ImageRepository;
import com.UdemyDemo.Udemy.Project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@Slf4j
@AllArgsConstructor
public class ImageServiceIml {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;


    public ImageModel uploadImageToUser(MultipartFile file, Principal principal) throws IOException {
        User user = getUserByPrincipal(principal);
        log.info("Uploading image profile to User {}", user.getUsername());
        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
        if (ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }
        ImageModel image = new ImageModel();
        image.setUserId(user.getId());
        image.setImageBytes(compressBytes(file.getBytes()));
        return imageRepository.save(image);
    }

    public ImageModel uploadImageToCourse(MultipartFile file,
                                          Principal principal, Long courseId) throws IOException {
        User user = getUserByPrincipal(principal);
        Course course = user.getCourses()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .collect(toSingleImageCollector());
        ImageModel image = new ImageModel();
        image.setCourseId(course.getId());
        image.setImageBytes(file.getBytes());
        image.setImageBytes(compressBytes(file.getBytes()));

        log.info("Uploading image to Course {}", course.getId());
        return imageRepository.save(image);
    }

    public ImageModel getImageToUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        ImageModel imageModel = imageRepository.findByUserId(user.getId()).orElse(null);
        if (ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }

    public ImageModel getImageToCourse(Long courseId) {
        ImageModel imageModel = imageRepository.findByCourseId(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found : " + courseId));

        if (ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }


    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found" + username));
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            log.error("Cannot compress Bytes");
        }
        System.out.println("Cannot compress Image byte size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            log.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }

    private <T> Collector<T, ?, T> toSingleImageCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
}

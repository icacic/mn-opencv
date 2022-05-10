package com.example.controller;

import java.io.IOException;

import com.example.service.FaceDetectionService;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;

@Controller("/api")
public class MainController {

    @Inject
    FaceDetectionService faceDetectionService;

    @Get("/image")
    public String greet() {

        faceDetectionService.detectFace();
        return "Job done";
    }

    @Post(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA, processes = MediaType.IMAGE_JPEG)
    public byte[] postImage(CompletedFileUpload file) {
        
        byte[] bytes= null;
        try { 
            bytes = faceDetectionService.detectFaceApi(file.getBytes()).toImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes; 

    }
}

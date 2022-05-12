package com.example.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.FaceModel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;

@Singleton
public class FaceDetectionService {

    private final static Logger logger = LoggerFactory.getLogger(FaceDetectionService.class);
    private List<FaceModel> faceEntities;
    private Mat image;
    
    public void detectFace() {
        Mat loadedImage = Imgcodecs.imread("./src/main/resources/images/legends.jpg");
        
        MatOfRect facesDetected = new MatOfRect();
        CascadeClassifier cascadeClassifier = new CascadeClassifier();
        int minFaceSize = Math.round(loadedImage.rows() * 0.1f);
        cascadeClassifier.load("./src/main/resources/haarcascades/haarcascade_frontalface_alt.xml");
        cascadeClassifier.detectMultiScale(loadedImage,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size());

        Rect[] facesArray = facesDetected.toArray();
        for (Rect face : facesArray) {
            Imgproc.rectangle(loadedImage, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
        }
        Imgcodecs.imwrite("./src/main/resources/images/faces.jpg", loadedImage);
    }

    public FaceDetectionService detectFaceApi(byte[] fileBytes) throws IOException {
        faceEntities=new ArrayList<>();
        MatOfRect faceDetections = new MatOfRect();
        CascadeClassifier faceDetector = new CascadeClassifier("./src/main/resources/haarcascades/haarcascade_frontalface_alt.xml");

        image = Imgcodecs.imdecode(new MatOfByte(fileBytes), Imgcodecs.IMREAD_UNCHANGED);
        faceDetector.detectMultiScale(image, faceDetections);

        logger.info(String.format("Detected %s faces", faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {
            faceEntities.add(new FaceModel(rect.x, rect.y, rect.width, rect.height, 0));
        }
        return this;
    }

    public byte[] toImage() {
        for (FaceModel fc : faceEntities) {
            Imgproc.rectangle(image, new Point(fc.getX(), fc.getY()), new Point(fc.getX() + fc.getWidth(), fc.getY() + fc.getHeight()), new Scalar(0, 255, 0));
        }
        return mat2Image(image);
    }

    private byte[] mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
    }

}

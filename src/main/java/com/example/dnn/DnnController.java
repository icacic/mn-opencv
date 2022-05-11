package com.example.dnn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;

@Controller("/api/objects")
public class DnnController {
    @Inject
    DnnProcessor processor;
    List<DnnObject> detectObject = new ArrayList<DnnObject>();

    @Post(value = "/main", consumes = MediaType.MULTIPART_FORM_DATA, processes = MediaType.IMAGE_JPEG)
    public byte[] postImage(CompletedFileUpload file) throws IOException {

        Mat frame = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_UNCHANGED);
        detectObject = processor.getObjectsInFrame(frame, false);
        for (DnnObject obj : detectObject) {
            Imgproc.rectangle(frame, obj.getLeftBottom(), obj.getRightTop(), new Scalar(255, 0, 0), 2);
            Imgproc.putText(frame, obj.getObjectName(), obj.getLeftBottom(), Imgproc.FONT_HERSHEY_PLAIN, 2, new Scalar (255, 255, 255));
        }
        return mat2Image(frame);

    }

    private static byte[] mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
    }
}

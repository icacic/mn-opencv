package com.example;

import io.micronaut.runtime.Micronaut;
import nu.pattern.OpenCV;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
        OpenCV.loadLocally();
    }
}

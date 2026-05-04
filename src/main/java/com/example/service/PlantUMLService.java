package com.example.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class PlantUMLService {

    // Encode PlantUML text to URL-safe format
    public static String encode(String text) {
        return Base64.getEncoder()
                .encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String toPlantUMLImageUrl(String plantUmlScript) {
        String encoded = encode(plantUmlScript);

        return "https://www.plantuml.com/plantuml/png/" + encoded;
    }
}
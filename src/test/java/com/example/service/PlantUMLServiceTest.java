package com.example.service;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;

class PlantUMLServiceTest {

    @Test
    void shouldEncodeTextToBase64() {
        String text = "@startuml\nStelios -> Greg\n@enduml";
        String expected = Base64.getEncoder()
                .encodeToString(text.getBytes(StandardCharsets.UTF_8));

        String result = PlantUMLService.encode(text);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    // TEST GENERATE IMAGE URL
    @Test
    void shouldGeneratePlantUMLImageUrl() {
        String script = "@startuml\nStelios -> Greg\n@enduml";
        String encoded = PlantUMLService.encode(script);
        String result = PlantUMLService.toPlantUMLImageUrl(script);

        assertNotNull(result);
        assertTrue(result.startsWith("https://www.plantuml.com/plantuml/png/"));
        assertTrue(result.contains(encoded));
    }

    // TEST EMPTY STRING ENCODE
    @Test
    void shouldEncodeEmptyString() {
        String result = PlantUMLService.encode("");

        assertNotNull(result);
        assertEquals("", result);
    }

    // TEST EMPTY SCRIPT URL
    @Test
    void shouldGenerateUrlForEmptyScript() {
        String result = PlantUMLService.toPlantUMLImageUrl("");

        assertEquals(
                "https://www.plantuml.com/plantuml/png/",
                result
        );
    }

    //SPECIAL CHARACTERS
    @Test
    void shouldEncodeSpecialCharactersCorrectly() {
        String text = "PlantUML Test !@#$%^&*()";
        String expected = Base64.getEncoder()
                .encodeToString(text.getBytes(StandardCharsets.UTF_8));

        String result = PlantUMLService.encode(text);
        assertEquals(expected, result);
    }
}
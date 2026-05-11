package com.example.service;

import com.example.entity.CRCCard;
import com.example.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class CRCDiagramGeneratorServiceTest {

    private CRCDiagramGeneratorService service;

    @BeforeEach
    void setup() {
        service = new CRCDiagramGeneratorService();
    }

    // TEST GENERATE UML WITH CRC CARDS
    @Test
    void shouldGeneratePlantUMLDiagram() {
        CRCCard card1 = new CRCCard();
        card1.setClassName("User Service");
        card1.setResponsibilities("Handle login");
        card1.setCollaborators("Database");
        CRCCard card2 = new CRCCard();
        card2.setClassName("Payment Manager");
        card2.setResponsibilities("Process payments");
        card2.setCollaborators("Bank API");
        Project project = new Project();
        project.setCrcCards(Arrays.asList(card1, card2));
        String result = service.generatePlantUML(project);

        assertNotNull(result);
        assertTrue(result.contains("@startuml"));
        assertTrue(result.contains("@enduml"));

        assertTrue(result.contains("class User_Service"));
        assertTrue(result.contains("Responsibilities:"));
        assertTrue(result.contains("Handle login"));
        assertTrue(result.contains("Collaborators:"));
        assertTrue(result.contains("Database"));

        assertTrue(result.contains("class Payment_Manager"));
        assertTrue(result.contains("Process payments"));
        assertTrue(result.contains("Bank API"));
    }

    // TEST EMPTY CRC CARD LIST
    @Test
    void shouldGenerateEmptyDiagramWhenNoCardsExist() {
        Project project = new Project();
        project.setCrcCards(Collections.emptyList());
        String result = service.generatePlantUML(project);

        assertNotNull(result);
        assertTrue(result.contains("@startuml"));
        assertTrue(result.contains("@enduml"));
        assertFalse(result.contains("class"));
    }

    // TEST CLASS NAME WITH SPACES
    @Test
    void shouldReplaceSpacesWithUnderscoresInClassName() {
        CRCCard card = new CRCCard();
        card.setClassName("Order Service");
        card.setResponsibilities("Manage orders");
        card.setCollaborators("Database");
        Project project = new Project();
        project.setCrcCards(Collections.singletonList(card));

        String result = service.generatePlantUML(project);
        assertTrue(result.contains("class Order_Service"));
    }

    // TEST NULL RESPONSIBILITIES
    @Test
    void shouldHandleNullResponsibilities() {
        CRCCard card = new CRCCard();
        card.setClassName("Test Class");
        card.setResponsibilities(null);
        card.setCollaborators("API");
        Project project = new Project();
        project.setCrcCards(Collections.singletonList(card));
        String result = service.generatePlantUML(project);

        assertTrue(result.contains("class Test_Class"));
        assertTrue(result.contains("null"));
    }

    // TEST NULL COLLABORATORS
    @Test
    void shouldHandleNullCollaborators() {
        CRCCard card = new CRCCard();
        card.setClassName("Another Class");
        card.setResponsibilities("Testing");
        card.setCollaborators(null);
        Project project = new Project();
        project.setCrcCards(Collections.singletonList(card));
        String result = service.generatePlantUML(project);

        assertTrue(result.contains("class Another_Class"));
        assertTrue(result.contains("Testing"));
        assertTrue(result.contains("null"));
    }
}
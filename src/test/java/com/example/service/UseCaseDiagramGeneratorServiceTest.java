package com.example.service;

import com.example.entity.Project;
import com.example.entity.UseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class UseCaseDiagramGeneratorServiceTest {

    private UseCaseDiagramGeneratorService service;

    @BeforeEach
    void setup() {
        service = new UseCaseDiagramGeneratorService();
    }

    // TEST GENERATE UML WITH USE CASES
    @Test
    void shouldGeneratePlantUMLDiagram() {
        UseCase uc1 = new UseCase();
        uc1.setName("Login");
        uc1.setActors("User");

        UseCase uc2 = new UseCase();
        uc2.setName("Register Account");
        uc2.setActors("Admin");
        Project project = new Project();
        project.setUseCases(Arrays.asList(uc1, uc2));
        String result = service.generatePlantUML(project);
        assertNotNull(result);

        assertTrue(result.contains("@startuml"));
        assertTrue(result.contains("left to right direction"));
        assertTrue(result.contains("actor User"));
        assertTrue(result.contains("usecase Login as \"Login\""));
        assertTrue(result.contains("User --> Login"));
        assertTrue(result.contains("usecase Register_Account as \"Register Account\""));
        assertTrue(result.contains("User --> Register_Account"));
        assertTrue(result.contains("@enduml"));
    }

    // TEST WITH EMPTY USE CASE LIST
    @Test
    void shouldGenerateEmptyDiagramWhenNoUseCases() {
        Project project = new Project();
        project.setUseCases(Collections.emptyList());
        String result = service.generatePlantUML(project);

        assertNotNull(result);
        assertTrue(result.contains("@startuml"));
        assertTrue(result.contains("actor User"));
        assertTrue(result.contains("@enduml"));
        assertFalse(result.contains("usecase"));
    }

    // TEST USE CASE WITHOUT ACTORS
    @Test
    void shouldGenerateUseCaseWithoutAssociationWhenActorsAreEmpty() {
        UseCase uc = new UseCase();
        uc.setName("Checkout");
        uc.setActors("");
        Project project = new Project();
        project.setUseCases(Collections.singletonList(uc));
        String result = service.generatePlantUML(project);

        assertTrue(result.contains("usecase Checkout as \"Checkout\""));
        assertFalse(result.contains("User --> Checkout"));
    }

    // TEST USE CASE WITH NULL ACTORS
    @Test
    void shouldGenerateUseCaseWithoutAssociationWhenActorsAreNull() {
        UseCase uc = new UseCase();
        uc.setName("Payment");
        uc.setActors(null);
        Project project = new Project();
        project.setUseCases(Collections.singletonList(uc));
        String result = service.generatePlantUML(project);

        assertTrue(result.contains("usecase Payment as \"Payment\""));
        assertFalse(result.contains("User --> Payment"));
    }

    // TEST NAME WITH SPACES
    @Test
    void shouldReplaceSpacesWithUnderscoresInUseCaseName() {
        UseCase uc = new UseCase();
        uc.setName("Reset Password");
        uc.setActors("User");

        Project project = new Project();
        project.setUseCases(Collections.singletonList(uc));

        String result = service.generatePlantUML(project);

        assertTrue(result.contains("usecase Reset_Password as \"Reset Password\""));
        assertTrue(result.contains("User --> Reset_Password"));
    }
}
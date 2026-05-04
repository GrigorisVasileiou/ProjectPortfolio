package com.example.service;

import com.example.entity.Project;
import com.example.entity.UseCase;
import org.springframework.stereotype.Service;

@Service
public class UseCaseDiagramGeneratorService {

    public String generatePlantUML(Project project) {

        StringBuilder sb = new StringBuilder();

        sb.append("@startuml\n\n");
        sb.append("left to right direction\n\n");

        sb.append("actor User\n\n");

        for (UseCase uc : project.getUseCases()) {

            String useCaseName = uc.getName().replace(" ", "_");

            sb.append("usecase ").append(useCaseName)
                    .append(" as \"").append(uc.getName()).append("\"\n");

            if (uc.getActors() != null && !uc.getActors().isEmpty()) {
                sb.append("User --> ").append(useCaseName).append("\n");
            }
        }

        sb.append("\n@enduml");

        return sb.toString();
    }
}
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

        // for all the use cases of the project
        for (UseCase uc : project.getUseCases()) {

            String useCaseName = uc.getName().replace(" ", "_");

            // use case declaration first
            sb.append("usecase ").append(useCaseName).append(" as \"").append(uc.getName()).append("\"\n\n");

            // actor
            if (uc.getActors() != null && !uc.getActors().isBlank()) {

                String actorName = uc.getActors().replace(" ", "_");

                sb.append("actor ").append(actorName).append("\n");

                sb.append(actorName).append(" --> ").append(useCaseName).append("\n\n");
            }

            // note
            sb.append("note right of ").append(useCaseName).append("\n");

            sb.append("Preconditions: ").append(uc.getPreconditions()).append("\n");

            sb.append("Main Flow: ").append(uc.getMainFlow()).append("\n");

            sb.append("Postconditions: ").append(uc.getPostconditions()).append("\n");

            sb.append("end note\n\n");
        }

        sb.append("@enduml");

        return sb.toString();
    }
}
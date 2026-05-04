package com.example.service;

import com.example.entity.CRCCard;
import com.example.entity.Project;
import org.springframework.stereotype.Service;

@Service
public class CRCDiagramGeneratorService {

    public String generatePlantUML(Project project) {

        StringBuilder sb = new StringBuilder();

        sb.append("@startuml\n\n");

        for (CRCCard card : project.getCrcCards()) {

            String className = card.getClassName().replace(" ", "_");

            sb.append("class ").append(className).append(" {\n");

            sb.append("  Responsibilities:\n");
            sb.append("  ").append(card.getResponsibilities()).append("\n\n");

            sb.append("  Collaborators:\n");
            sb.append("  ").append(card.getCollaborators()).append("\n");

            sb.append("}\n\n");
        }

        sb.append("@enduml");

        return sb.toString();
    }
}
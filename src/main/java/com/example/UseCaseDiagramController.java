package com.example;

import com.example.entity.Project;
import com.example.repository.ProjectRepository;
import com.example.service.UseCaseDiagramGeneratorService;
import com.example.service.PlantUMLService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagram/usecases")
public class UseCaseDiagramController {

    private final ProjectRepository projectRepository;
    private final UseCaseDiagramGeneratorService service;
    private final PlantUMLService plantUMLService;

    public UseCaseDiagramController(ProjectRepository projectRepository,
                                    UseCaseDiagramGeneratorService service,
                                    PlantUMLService plantUMLService) {
        this.projectRepository = projectRepository;
        this.service = service;
        this.plantUMLService = plantUMLService;
    }

    @GetMapping("/image/{projectId}")
    public String generateImage(@PathVariable Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow();

        String script = service.generatePlantUML(project);

        return plantUMLService.toPlantUMLImageUrl(script);
    }

    @GetMapping("/{projectId}")
    public String generate(@PathVariable Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow();

        return service.generatePlantUML(project);
    }
}
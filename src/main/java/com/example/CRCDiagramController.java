package com.example;

import com.example.entity.Project;
import com.example.repository.ProjectRepository;
import com.example.service.CRCDiagramGeneratorService;
import com.example.service.PlantUMLService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagram/crc")
public class CRCDiagramController {

    private final ProjectRepository projectRepository;
    private final CRCDiagramGeneratorService service;

    public CRCDiagramController(ProjectRepository projectRepository,
                                CRCDiagramGeneratorService service) {
        this.projectRepository = projectRepository;
        this.service = service;
    }

    @GetMapping("/image/{projectId}")
    public String generateCRCImage(@PathVariable Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow();

        String script = service.generatePlantUML(project);

        return PlantUMLService.toPlantUMLImageUrl(script);
    }

    @GetMapping("/{projectId}")
    public String generate(@PathVariable Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow();

        return service.generatePlantUML(project);
    }
}
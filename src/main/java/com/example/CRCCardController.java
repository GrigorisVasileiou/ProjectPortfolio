package com.example;

import com.example.entity.Project;
import com.example.entity.UseCase;
import com.example.service.CRCCardService;
import com.example.service.ProjectService;
import com.example.service.UseCaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CRCCardController {

    private final CRCCardService crcCardService;
    private final ProjectService projectService;
    private final UseCaseService useCaseService;

    public CRCCardController(
            CRCCardService crcCardService,
            ProjectService projectService,
            UseCaseService useCaseService
    ) {
        this.crcCardService = crcCardService;
        this.projectService = projectService;
        this.useCaseService = useCaseService;
    }

    // US11 - εμφάνιση φόρμας δημιουργίας
    @GetMapping("/crc/create/{projectId}")
    public String showCreateForm(@PathVariable Long projectId, Model model) {

        Project project = projectService.getById(projectId);

        model.addAttribute("project", project);
        model.addAttribute("usecases", useCaseService.getByProject(project));

        return "createCRCCard";
    }

    // US11 + US13
    @PostMapping("/crc/create")
    public String createCard(
            @RequestParam String className,
            @RequestParam String responsibilities,
            @RequestParam String collaborators,
            @RequestParam Long projectId,
            @RequestParam(required = false) Long useCaseId
    ) {

        Project project = projectService.getById(projectId);

        UseCase useCase = null;
        if (useCaseId != null) {
            useCase = useCaseService.getById(useCaseId);
        }

        crcCardService.createCard(
                className,
                responsibilities,
                collaborators,
                project,
                useCase
        );

        return "redirect:/crc/" + projectId;
    }

    // Προβολή όλων των CRC cards ενός project
    @GetMapping("/crc/{projectId}")
    public String manageCards(@PathVariable Long projectId, Model model) {

        Project project = projectService.getById(projectId);

        model.addAttribute("project", project);
        model.addAttribute("cards", crcCardService.getByProject(project));

        return "manageCRCCards";
    }

    // US12
    @PostMapping("/crc/update")
    public String updateCard(
            @RequestParam Long id,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String responsibilities,
            @RequestParam(required = false) String collaborators
    ) {

        crcCardService.updateCard(id, className, responsibilities, collaborators);

        Long projectId = crcCardService.getById(id).getProject().getId();

        return "redirect:/crc/" + projectId;
    }

    // US14
    @PostMapping("/crc/delete")
    public String deleteCard(@RequestParam Long id) {

        Long projectId = crcCardService.getById(id).getProject().getId();

        crcCardService.deleteCard(id);

        return "redirect:/crc/" + projectId;
    }
}
package com.example;

import com.example.entity.CRCCard;
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

        return "redirect:/crc/create/" + projectId;
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
            @RequestParam(required = false) String collaborators,
            @RequestParam(required = false) Long useCaseId
    ) {

        Project project = crcCardService.getById(id).getProject();

        UseCase useCase = null;
        if (useCaseId != null) {
            useCase = useCaseService.getById(useCaseId);
        }

        CRCCard card = crcCardService.getById(id);

        card.setClassName(className);
        card.setResponsibilities(responsibilities);
        card.setCollaborators(collaborators);
        card.setUseCase(useCase);

        crcCardService.save(card);

        return "redirect:/crc/" + project.getId();
    }

    @GetMapping("/crc/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        CRCCard card = crcCardService.getById(id);

        model.addAttribute("card", card);
        model.addAttribute("project", card.getProject());
        model.addAttribute("usecases",
                useCaseService.getByProject(card.getProject()));

        return "editCRCCard";
    }

    // US14
    @PostMapping("/crc/delete")
    public String deleteCard(@RequestParam Long id) {

        Long projectId = crcCardService.getById(id).getProject().getId();

        crcCardService.deleteCard(id);

        return "redirect:/crc/" + projectId;
    }
}
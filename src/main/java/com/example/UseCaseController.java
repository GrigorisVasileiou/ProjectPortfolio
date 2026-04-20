package com.example;

import com.example.entity.Project;
import com.example.service.ProjectService;
import com.example.service.UseCaseService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UseCaseController {

    private final UseCaseService useCaseService;
    private final ProjectService projectService;

    public UseCaseController(UseCaseService useCaseService, ProjectService projectService) {
        this.useCaseService = useCaseService;
        this.projectService = projectService;
    }

    // ✅ CREATE (US7)
    @PostMapping("/usecases/create")
    public String createUseCase(
            @RequestParam String name,
            @RequestParam String actors,
            @RequestParam String preconditions,
            @RequestParam String mainFlow,
            @RequestParam String postconditions,
            @RequestParam Long projectId
    ) {
        Project project = projectService.getById(projectId);

        useCaseService.createUseCase(name, actors, preconditions, mainFlow, postconditions, project);

        return "redirect:/usecases/create/" + project.getId();
    }

    @GetMapping("/usecases/create/{projectId}")
    public String showCreateForm(@PathVariable Long projectId, Model model) {
        Project project = projectService.getById(projectId);
        model.addAttribute("project", project);
        return "createUseCase";
    }

    // ✅ GET (US9)
    @GetMapping("/usecases/{projectId}")
    public String getUseCases(@PathVariable Long projectId, Model model) {

        Project project = projectService.getById(projectId);

        model.addAttribute("usecases", useCaseService.getByProject(project));
        model.addAttribute("project", project);

        return "manageUseCases";
    }

    // ✅ UPDATE (US8)
    @PostMapping("/usecases/update")
    public String updateUseCase(
            @RequestParam Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String actors,
            @RequestParam(required = false) String preconditions,
            @RequestParam(required = false) String mainFlow,
            @RequestParam(required = false) String postconditions
    ) {
        useCaseService.updateUseCase(
                id,
                name,
                actors,
                preconditions,
                mainFlow,
                postconditions
        );
        Long projectId = useCaseService.getById(id).getProject().getId();
        return "redirect:/usecases/" + projectId;
    }

    // ✅ DELETE (US10)
    @PostMapping("/usecases/delete")
    public String deleteUseCase(@RequestParam Long id) {
        Long projectId = useCaseService.getById(id).getProject().getId();
        useCaseService.deleteUseCase(id);
        return "redirect:/usecases/" + projectId;
    }
}
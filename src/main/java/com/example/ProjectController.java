package com.example;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.service.CRCCardService;
import com.example.service.ProjectService;
import com.example.service.UseCaseService;
import com.example.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final UseCaseService useCaseService;
    private final CRCCardService crcCardService;

    public ProjectController(ProjectService projectService,
                             UserService userService,
                             UseCaseService useCaseService,
                             CRCCardService crcCardService) {
        this.projectService = projectService;
        this.userService = userService;
        this.useCaseService = useCaseService;
        this.crcCardService = crcCardService;
    }

    @GetMapping("/projects")
    public String projects(@AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
                           Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects", projectService.getProjectsByUser(user));
        return "projects";
    }

    @PostMapping("/projects/create")
    public String create(@RequestParam String name,
                         @RequestParam String description,
                         @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        projectService.createProject(name, description, user);
        return "redirect:/projects";
    }

    @PostMapping("/projects/delete")
    public String delete(@RequestParam Long id,
                         @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        projectService.deleteProject(id, user);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectService.getById(id);

        model.addAttribute("project", project);
        model.addAttribute("usecases", useCaseService.getByProject(project));
        model.addAttribute("cards", crcCardService.getByProject(project));
        return "project-details";
    }

    @GetMapping("/uml")
    public String umlPage(@RequestParam Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "uml";
    }
}
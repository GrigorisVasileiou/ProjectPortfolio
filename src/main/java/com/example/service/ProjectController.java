package com.example;

import com.example.entity.User;
import com.example.service.ProjectService;
import com.example.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    // US4
    @GetMapping("/projects")
    public String projects(@AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser,
                           Model model) {

        User user = userService.findByUsername(currentUser.getUsername());
        model.addAttribute("projects", projectService.getProjectsByUser(user));

        return "projects";
    }

    // US5
    @PostMapping("/projects/create")
    public String create(@RequestParam String name,
                         @RequestParam String description,
                         @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {

        User user = userService.findByUsername(currentUser.getUsername());
        projectService.createProject(name, description, user);

        return "redirect:/projects";
    }

    // US6
    @PostMapping("/projects/delete")
    public String delete(@RequestParam Long id,
                         @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {

        User user = userService.findByUsername(currentUser.getUsername());
        projectService.deleteProject(id, user);

        return "redirect:/projects";
    }
}
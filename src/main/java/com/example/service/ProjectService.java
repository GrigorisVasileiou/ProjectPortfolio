package com.example.service;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public void createProject(String name, String description, User user) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setUser(user);
        projectRepository.save(project);
    }

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public void deleteProject(Long id, User user) {
        Project project = projectRepository.findById(id).orElse(null);

        if (project != null && project.getUser().getId().equals(user.getId())) {
            projectRepository.delete(project);
        }
    }

    public List<Project> getProjectsByUser(User user) {
        return projectRepository.findByUser(user);
    }

    public Project getById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }
}
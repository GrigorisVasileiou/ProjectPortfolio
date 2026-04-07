package com.example.service;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private ProjectRepository projectRepository;
    private ProjectService projectService;

    @BeforeEach
    void setup() {
        projectRepository = Mockito.mock(ProjectRepository.class);
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void shouldCreateProject() {
        User user = new User();
        user.setId(1L);

        projectService.createProject("Test", "Desc", user);

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void shouldReturnAllProjects() {
        List<Project> mockProjects = Arrays.asList(new Project(), new Project());

        when(projectRepository.findAll()).thenReturn(mockProjects);

        List<Project> result = projectService.getAllProjects();

        assertEquals(2, result.size());
    }

    @Test
    void shouldDeleteProjectIfOwnerMatches() {
        User user = new User();
        user.setId(1L);

        Project project = new Project();
        project.setId(10L);
        project.setUser(user);

        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));

        projectService.deleteProject(10L, user);

        verify(projectRepository, times(1)).delete(project);
    }

    @Test
    void shouldNotDeleteProjectIfUserIsDifferent() {
        User owner = new User();
        owner.setId(1L);

        User otherUser = new User();
        otherUser.setId(2L);

        Project project = new Project();
        project.setId(10L);
        project.setUser(owner);

        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));

        projectService.deleteProject(10L, otherUser);

        verify(projectRepository, never()).delete(any());
    }

    @Test
    void shouldReturnProjectsByUser() {
        User user = new User();
        List<Project> projects = Arrays.asList(new Project(), new Project());

        when(projectRepository.findByUser(user)).thenReturn(projects);

        List<Project> result = projectService.getProjectsByUser(user);

        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnProjectById() {
        Project project = new Project();
        project.setId(5L);

        when(projectRepository.findById(5L)).thenReturn(Optional.of(project));

        Project result = projectService.getById(5L);

        assertNotNull(result);
        assertEquals(5L, result.getId());
    }
}
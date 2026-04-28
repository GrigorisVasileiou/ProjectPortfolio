package com.example.service;

import com.example.entity.Project;
import com.example.entity.UseCase;
import com.example.repository.UseCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UseCaseServiceTest {

    private UseCaseRepository useCaseRepository;
    private UseCaseService useCaseService;

    @BeforeEach
    void setup() {
        useCaseRepository = mock(UseCaseRepository.class);
        useCaseService = new UseCaseService(useCaseRepository);
    }

    //TEST CREATE
    @Test
    void shouldCreateUseCase() {
        Project project = new Project();
        useCaseService.createUseCase(
                "Login",
                "User",
                "None",
                "User logs in",
                "Success",
                project
        );

        verify(useCaseRepository).save(argThat(uc ->
                uc.getName().equals("Login") &&
                        uc.getActors().equals("User") &&
                        uc.getPreconditions().equals("None") &&
                        uc.getMainFlow().equals("User logs in") &&
                        uc.getPostconditions().equals("Success") &&
                        uc.getProject() == project
        ));
    }

    //TEST GET BY ID (SUCCESS)
    @Test
    void shouldReturnUseCaseById() {
        UseCase uc = new UseCase();
        uc.setId(1L);

        when(useCaseRepository.findById(1L)).thenReturn(Optional.of(uc));
        UseCase result = useCaseService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    //TEST GET BY ID (FAIL)
    @Test
    void shouldThrowExceptionWhenUseCaseNotFound() {
        when(useCaseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            useCaseService.getById(1L);
        });
    }

    //TEST GET BY PROJECT
    @Test
    void shouldReturnUseCasesByProject() {
        Project project = new Project();
        List<UseCase> list = Arrays.asList(new UseCase(), new UseCase());
        when(useCaseRepository.findByProject(project)).thenReturn(list);
        List<UseCase> result = useCaseService.getByProject(project);
        assertSame(list, result);
    }

    //TEST UPDATE (exists)
    @Test
    void shouldUpdateUseCase() {
        UseCase uc = new UseCase();
        uc.setId(1L);

        when(useCaseRepository.findById(1L)).thenReturn(Optional.of(uc));
        useCaseService.updateUseCase(
                1L,
                "New Name",
                "New Actors",
                "pre",
                "main",
                "post"
        );
        assertEquals("New Name", uc.getName());
        assertEquals("New Actors", uc.getActors());
        assertEquals("pre", uc.getPreconditions());
        assertEquals("main", uc.getMainFlow());
        assertEquals("post", uc.getPostconditions());
        verify(useCaseRepository).save(uc);
    }

    //TEST UPDATE (not exists)
    @Test
    void shouldNotUpdateIfUseCaseNotFound() {
        when(useCaseRepository.findById(1L)).thenReturn(Optional.empty());
        useCaseService.updateUseCase(
                1L,
                "New Name",
                "Actors",
                "pre",
                "main",
                "post"
        );
        verify(useCaseRepository, never()).save(any());
    }

    //TEST PARTIAL UPDATE
    @Test
    void shouldUpdateUseCasePartial() {
        UseCase uc = new UseCase();
        uc.setId(1L);
        uc.setName("Old");
        uc.setActors("OldActors");

        when(useCaseRepository.findById(1L)).thenReturn(Optional.of(uc));
        useCaseService.updateUseCasePartial(1L, "New", "");
        assertEquals("New", uc.getName());
        assertEquals("OldActors", uc.getActors());
        verify(useCaseRepository).save(uc);
    }

    // TEST DELETE (SUCCESS)
    @Test
    void shouldDeleteUseCase() {
        UseCase uc = new UseCase();
        uc.setId(1L);
        uc.setCrcCards(new ArrayList<>());

        when(useCaseRepository.findById(1L)).thenReturn(Optional.of(uc));

        useCaseService.deleteUseCase(1L);

        verify(useCaseRepository).delete(uc);
    }

    // TEST DELETE (FAIL)
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingUseCase() {
        when(useCaseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            useCaseService.deleteUseCase(1L);
        });
    }

    // TEST GET BY IDS
    @Test
    void shouldReturnUseCasesByIds() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<UseCase> list = Arrays.asList(new UseCase(), new UseCase());

        when(useCaseRepository.findAllById(ids)).thenReturn(list);

        List<UseCase> result = useCaseService.getByIds(ids);

        assertEquals(2, result.size());
    }
}
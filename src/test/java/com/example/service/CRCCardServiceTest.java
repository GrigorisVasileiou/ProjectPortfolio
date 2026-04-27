package com.example.service;

import com.example.entity.CRCCard;
import com.example.entity.Project;
import com.example.entity.UseCase;
import com.example.repository.CRCCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CRCCardServiceTest {

    private CRCCardRepository crcCardRepository;
    private CRCCardService crcCardService;

    @BeforeEach
    void setup() {
        crcCardRepository = mock(CRCCardRepository.class);
        crcCardService = new CRCCardService(crcCardRepository);
    }

    //TEST CREATE
    @Test
    void shouldCreateCard() {
        Project project = new Project();
        //UseCase useCases = new UseCase();
        List<UseCase> useCases = Arrays.asList(new UseCase());

        crcCardService.createCard(
                "User",
                "Login responsibility",
                "Database",
                project,
                useCases
        );
        verify(crcCardRepository, times(1)).save(any(CRCCard.class));
    }

    //TEST GET BY ID (SUCCESS)
    @Test
    void shouldReturnCardById() {
        CRCCard card = new CRCCard();
        card.setId(1L);

        when(crcCardRepository.findById(1L)).thenReturn(Optional.of(card));
        CRCCard result = crcCardService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    //TEST GET BY ID (NOT FOUND)
    @Test
    void shouldThrowExceptionWhenCardNotFound() {
        when(crcCardRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            crcCardService.getById(1L);
        });
        assertEquals("CRC Card not found", ex.getMessage());
    }

    //TEST GET BY PROJECT
    @Test
    void shouldReturnCardsByProject() {
        Project project = new Project();

        List<CRCCard> cards = Arrays.asList(
                new CRCCard(),
                new CRCCard()
        );
        when(crcCardRepository.findByProject(project)).thenReturn(cards);
        List<CRCCard> result = crcCardService.getByProject(project);
        assertEquals(2, result.size());
    }

    //TEST GET BY USE CASE
    @Test
    void shouldReturnCardsByUseCase() {
        UseCase useCase = new UseCase();

        List<CRCCard> cards = Arrays.asList(
                new CRCCard(),
                new CRCCard(),
                new CRCCard()
        );
        when(crcCardRepository.findByUseCasesContains(useCase)).thenReturn(cards);
        List<CRCCard> result = crcCardService.getByUseCase(useCase);
        assertEquals(3, result.size());
    }

    //TEST UPDATE ALL FIELDS
    @Test
    void shouldUpdateCard() {
        CRCCard card = new CRCCard();
        card.setId(1L);
        card.setClassName("Old");
        card.setResponsibilities("OldResp");
        card.setCollaborators("OldCollab");

        when(crcCardRepository.findById(1L)).thenReturn(Optional.of(card));
        crcCardService.updateCard(
                1L,
                "NewClass",
                "NewResp",
                "NewCollab",
                null
        );
        assertEquals("NewClass", card.getClassName());
        assertEquals("NewResp", card.getResponsibilities());
        assertEquals("NewCollab", card.getCollaborators());
        verify(crcCardRepository, times(1)).save(card);
    }

    //TEST UPDATE ONLY SOME FIELDS
    @Test
    void shouldUpdateOnlyNonBlankFields() {
        CRCCard card = new CRCCard();
        card.setId(1L);
        card.setClassName("OldClass");
        card.setResponsibilities("OldResp");
        card.setCollaborators("OldCollab");

        when(crcCardRepository.findById(1L)).thenReturn(Optional.of(card));
        crcCardService.updateCard(
                1L,
                "",
                "NewResp",
                null,
                null
        );
        assertEquals("", card.getClassName());
        assertEquals("NewResp", card.getResponsibilities());
        assertNull(card.getCollaborators());
        verify(crcCardRepository).save(card);
    }

    //TEST DELETE
    @Test
    void shouldDeleteCard() {
        CRCCard card = new CRCCard();
        card.setId(1L);
        card.setUseCases(new ArrayList<>()); // σημαντικό για να μην σκάσει στο clear()
        when(crcCardRepository.findById(1L)).thenReturn(Optional.of(card));
        crcCardService.deleteCard(1L);
        verify(crcCardRepository, times(1)).delete(card);
    }

    //TEST SAVE METHOD
    @Test
    void shouldSaveCard() {
        CRCCard card = new CRCCard();
        crcCardService.save(card);
        verify(crcCardRepository, times(1)).save(card);
    }
}
package com.example.service;
import com.example.entity.CRCCard;
import com.example.entity.Project;
import com.example.entity.UseCase;
import com.example.repository.CRCCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CRCCardService {

    private final CRCCardRepository crcCardRepository;

    public CRCCardService(CRCCardRepository crcCardRepository) {
        this.crcCardRepository = crcCardRepository;
    }

    public CRCCard getById(Long id) {
        return crcCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CRC Card not found"));
    }

    // US11 - Create CRC Card
    public void createCard(
            String className,
            String responsibilities,
            String collaborators,
            Project project,
            List<UseCase> useCases
    ) {
        CRCCard card = new CRCCard();
        card.setClassName(className);
        card.setResponsibilities(responsibilities);
        card.setCollaborators(collaborators);
        card.setProject(project);
        card.setUseCases(useCases);

        crcCardRepository.save(card);
    }

    // US13 - Get cards by project
    public List<CRCCard> getByProject(Project project) {
        return crcCardRepository.findByProject(project);
    }

    // US13 - Get cards by use case
    public List<CRCCard> getByUseCase(UseCase useCase) {
        return crcCardRepository.findByUseCasesContains(useCase);
    }

    // US12 - Update
    public void updateCard(
            Long id,
            String className,
            String responsibilities,
            String collaborators,
            List<UseCase> useCases
    ) {
        CRCCard card = getById(id);

        if (className != null && !className.isBlank()) {
            card.setClassName(className);
        }

        if (responsibilities != null && !responsibilities.isBlank()) {
            card.setResponsibilities(responsibilities);
        }

        if (collaborators != null && !collaborators.isBlank()) {
            card.setCollaborators(collaborators);
        }

        if (useCases != null) {
            card.setUseCases(useCases);
        }

        crcCardRepository.save(card);
    }

    // US14 - Delete
    public void deleteCard(Long id) {
        CRCCard card = getById(id);
        card.getUseCases().clear();
        crcCardRepository.delete(card);
    }

    public void save(CRCCard card) {
        crcCardRepository.save(card);
    }
}
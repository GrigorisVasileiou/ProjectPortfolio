package com.example.service;

import com.example.entity.Project;
import com.example.entity.UseCase;
import com.example.repository.UseCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UseCaseService {

    private final UseCaseRepository useCaseRepository;

    public UseCaseService(UseCaseRepository useCaseRepository) {
        this.useCaseRepository = useCaseRepository;
    }

    public UseCase getById(Long id) {
        return useCaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UseCase not found."));
    }

    //US7 - CREATE
    public void createUseCase(String name, String actors, String pre, String main, String post, Project project) {
        UseCase uc = new UseCase();
        uc.setName(name);
        uc.setActors(actors);
        uc.setPreconditions(pre);
        uc.setMainFlow(main);
        uc.setPostconditions(post);
        uc.setProject(project);

        useCaseRepository.save(uc);
    }

    //US9 - GET ALL
    public List<UseCase> getByProject(Project project) {
        return useCaseRepository.findByProject(project);
    }

    //US8 - UPDATE
    public void updateUseCase(Long id, String name, String actors) {
        UseCase uc = useCaseRepository.findById(id).orElse(null);

        if (uc != null) {
            uc.setName(name);
            uc.setActors(actors);
            useCaseRepository.save(uc);
        }
    }

    public void updateUseCasePartial(Long id, String name, String actors) {
        UseCase uc = useCaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UseCase not found"));

        if (name != null && !name.isBlank()) {
            uc.setName(name);
        }

        if (actors != null && !actors.isBlank()) {
            uc.setActors(actors);
        }

        useCaseRepository.save(uc);
    }

    //US10 - DELETE
    public void deleteUseCase(Long id) {
        useCaseRepository.deleteById(id);
    }
}
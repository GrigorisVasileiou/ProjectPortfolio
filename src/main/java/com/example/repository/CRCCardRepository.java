package com.example.repository;

import com.example.entity.CRCCard;
import com.example.entity.Project;
import com.example.entity.UseCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CRCCardRepository extends JpaRepository<CRCCard, Long> {

    List<CRCCard> findByProject(Project project);

    List<CRCCard> findByUseCasesContains(UseCase useCase);
}
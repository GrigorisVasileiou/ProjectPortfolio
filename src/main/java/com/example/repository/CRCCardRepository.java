package com.example.repository;

import com.example.entity.CRCCard;
import com.example.entity.Project;
import com.example.entity.UseCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CRCCardRepository extends JpaRepository<CRCCard, Long> {

    @Query("SELECT c FROM CRCCard c LEFT JOIN FETCH c.useCases WHERE c.project = :project")
    List<CRCCard> findByProject(@Param("project") Project project);

    List<CRCCard> findByUseCasesContains(UseCase useCase);
}
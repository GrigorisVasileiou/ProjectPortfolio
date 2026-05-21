package com.example.repository;

import com.example.entity.Project;
import com.example.entity.UseCase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UseCaseRepository extends JpaRepository<UseCase, Long> {

    // Το Spring Data JPA δημιουργει αυτοματα την υλοποιηση
    List<UseCase> findByProject(Project project);

}
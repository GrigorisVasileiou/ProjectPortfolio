package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "use_cases")
public class UseCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String actors;
    private String preconditions;
    private String mainFlow;
    private String postconditions;

    @ManyToOne
    private Project project;

    @ManyToMany(mappedBy = "useCases")
    private List<CRCCard> crcCards;
}
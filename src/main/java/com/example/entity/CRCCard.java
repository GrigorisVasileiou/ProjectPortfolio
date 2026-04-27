package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "CRC_Cards")
public class CRCCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    @Column(length = 2000)
    private String responsibilities;

    @Column(length = 2000)
    private String collaborators;

    @ManyToOne
    private Project project;

    @ManyToMany
    @JoinTable(
            name = "crc_usecase",
            joinColumns = @JoinColumn(name = "crc_id"),
            inverseJoinColumns = @JoinColumn(name = "usecase_id")
    )
    private List<UseCase> useCases;

    public void setUseCases(List<UseCase> useCases) {
        this.useCases = useCases;
    }
}
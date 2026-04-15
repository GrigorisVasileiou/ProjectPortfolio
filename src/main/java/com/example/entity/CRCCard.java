package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "crc_cards")
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

    @ManyToOne
    private UseCase useCase;
}
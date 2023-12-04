package com.example.gestionPrjt.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "description")
    private String description;
    @Column(name = " startDate")
    private LocalDate  startDate;
    @Column(name = "endDate")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority ;
    @Column(name = "tags")
    private String tags ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_collaborator")
    private Collaborator collaborator;
}

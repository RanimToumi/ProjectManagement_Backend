package com.example.gestionPrjt.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Column(name = "startDate")
    private LocalDate startDate;
    @Column(name = "EndDate")
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "id_manager")
    private Manager manager;



}

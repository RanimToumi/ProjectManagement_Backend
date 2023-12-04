package com.example.gestionPrjt.Dto;

import com.example.gestionPrjt.Models.Priority;
import com.example.gestionPrjt.Models.TaskStatus;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TaskDto {
    private Long id;
    private String nom;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskStatus status;
    private Priority priority ;
    private String tags ;
    private Long id_collaborator;


}

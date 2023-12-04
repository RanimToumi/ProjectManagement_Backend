package com.example.gestionPrjt.Repo;

import com.example.gestionPrjt.Models.Collaborator;
import com.example.gestionPrjt.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
package com.example.gestionPrjt.Repo;
import com.example.gestionPrjt.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByManagerId(Long userId);
}

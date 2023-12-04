package com.example.gestionPrjt.Services;

import com.example.gestionPrjt.Dto.ProjectDto;
import com.example.gestionPrjt.Exception.EntityNotFoundException;
import com.example.gestionPrjt.Exception.ErrorCode;
import com.example.gestionPrjt.Models.*;
import com.example.gestionPrjt.Repo.ManagerRepository;
import com.example.gestionPrjt.Repo.ProjectRepository;
import com.example.gestionPrjt.Repo.UserRepository;
import com.example.gestionPrjt.auth.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepo;
    private final UserRepository userRepository ;
    private final ManagerRepository managerRepository ;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public Optional<ProjectDto> findProjectById(Long projectId) {
        if (projectId.equals(0))
            {
                log.error("ProjectId is null");
                return Optional.empty();
            }
        else
            {
                Optional<Project> project = projectRepo.findById(projectId);
                return project.map(p -> modelMapper.map(p, ProjectDto.class));
            }
    }

    public List<ProjectDto> findAllProjects() {
        List<Project> projects = projectRepo.findAll();
        return projects.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }




    public List<ProjectDto> findAllProjectsByUser(Long userId) {
        if (userId.equals(0)) {
            log.error("UserId is null");
            return Collections.emptyList();
        } else {
            List<Project> projects = projectRepo.findAllByManagerId(userId);
            return projects.stream()
                    .map(p -> modelMapper.map(p, ProjectDto.class))
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public ProjectDto addProject(ProjectDto projectDto) {
        // Map the ProjectDto to Project
        Project project = modelMapper.map(projectDto, Project.class);
        logger.info("Adding project: {}", projectDto);
        if (projectDto.getId_manager() != null) {
            Optional<Manager> managerOptional = managerRepository.findById(projectDto.getId_manager());
            logger.info("Is Manager Present? {}", managerOptional.isPresent());
            if (managerOptional.isPresent()) {
                Manager manager = managerOptional.get();
                logger.info("Manager : {}", manager.getId());
            project.setManager(manager);}
        }
        // Save the Project
        Project savedProject = projectRepo.save(project);
        logger.info("project : {}", savedProject.getManager().getId());
        logger.info("Saved project: {}", savedProject);
        // Map the saved Project back to ProjectDto and return
        ProjectDto dto = modelMapper.map(savedProject, ProjectDto.class);
        dto.setId_manager(savedProject.getManager().getId());
        logger.info("dto: {}", dto);
        return dto;
    }

//    public ProjectDto addProject(ProjectDto projectDto) {
//        // Map the ProjectDto to Project
//        Project project = modelMapper.map(projectDto, Project.class);
//        logger.info("Adding project: {}", projectDto);
//
//        if (projectDto.getId_manager() != null) {
//            Optional<Manager> managerOptional = managerRepository.findById(projectDto.getId_manager());
//            managerOptional.ifPresent(manager -> project.setManager(manager));
//        }
//
//        // Save the Project
//        Project savedProject = projectRepo.save(project);
//        logger.info("Saved project: {}", savedProject);
//
//        // Map the saved Project back to ProjectDto and return
//        return modelMapper.map(savedProject, ProjectDto.class);
//    }


    private ProjectDto convertToDto(Project project) {
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        if (project.getManager() != null) {
            projectDto.setId_manager(project.getManager().getId());
        }
        return projectDto;
    }




    public ProjectDto updateProject( ProjectDto projectDto) {

        Project project = modelMapper.map(projectDto, Project.class);
        Project savedProject = projectRepo.save(project);
        return modelMapper.map(savedProject, ProjectDto.class);}


    public void deleteProjectById(Long projectId) {
        if (projectId.equals(0) )log.error("projectId is null");
        else
                projectRepo.deleteById(projectId);

    }

    public String getManagerNameForProject(Long projectId) {
        Optional<Project> project = projectRepo.findById(projectId);

        if (project.isPresent()) {
            Manager manager = project.get().getManager();

            if (manager != null) {
                return manager.getNom()+" "+manager.getPrenom();
            } else {
                return "Aucun manager associé à ce projet.";
            }
        } else {
            return "projet non trouvée.";
        }

    }
}

package com.example.gestionPrjt.Controllers;

import com.example.gestionPrjt.Dto.ProjectDto;
import com.example.gestionPrjt.Services.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/getProject/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long projectId) {
        return projectService.findProjectById(projectId)
                .map(projectDto -> new ResponseEntity<>(projectDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/getAllProjects")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.findAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    @GetMapping("/getAllProjectsByUser/{userId}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProjectDto>> getAllProjectsByUser(@PathVariable("userId") Long userId) {
        List<ProjectDto> projects = projectService.findAllProjectsByUser(userId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/getManagerNameForProject/{projectId}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getCollaboratorNameForTask(@PathVariable("projectId") Long projectId) {
        String collaboratorName = projectService.getManagerNameForProject(projectId);
        return new ResponseEntity<>(collaboratorName, HttpStatus.OK);
    }

    @PostMapping("/addProject")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectDto projectDto) {
        ProjectDto addedProject = projectService.addProject(projectDto);
        return new ResponseEntity<>(addedProject, HttpStatus.CREATED);
    }
    @PutMapping("update/{projectId}")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        if (!projectService.findProjectById(projectId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectDto.setId(projectId);
        ProjectDto updatedProject = projectService.updateProject(projectDto);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{projectId}")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        if (!projectService.findProjectById(projectId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            projectService.deleteProjectById(projectId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


}

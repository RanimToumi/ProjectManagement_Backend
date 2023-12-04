package com.example.gestionPrjt.Controllers;

import com.example.gestionPrjt.Dto.ManagerDto;
import com.example.gestionPrjt.Services.ManagerService;
import com.example.gestionPrjt.Services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;
    private final ProjectService projectService ;

    @GetMapping("/findManagerByProject/{ProjectId}")
    public ResponseEntity<ManagerDto>findManagerByProject(@PathVariable("ProjectId") Long ProjectId) {
        if (!projectService.findProjectById(ProjectId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
           Optional<ManagerDto> managerDto = managerService.findManagerByProject(ProjectId);
            return new ResponseEntity<ManagerDto>(managerDto.get(),HttpStatus.OK);
        }

    }

}

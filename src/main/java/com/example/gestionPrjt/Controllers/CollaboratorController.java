package com.example.gestionPrjt.Controllers;

import com.example.gestionPrjt.Dto.CollaboratorDto;
import com.example.gestionPrjt.Models.Collaborator;
import com.example.gestionPrjt.Services.CollaboratorService;
import com.example.gestionPrjt.Services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collaborator")
public class CollaboratorController {



}

package com.example.gestionPrjt.Services;

import com.example.gestionPrjt.Dto.CollaboratorDto;
import com.example.gestionPrjt.Dto.ManagerDto;

import com.example.gestionPrjt.Models.Manager;
import com.example.gestionPrjt.Repo.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository ;
    private final ModelMapper modelMapper ;

    public Optional<ManagerDto> findManagerByProject(Long projectId) {
            Optional<Manager> manager = managerRepository.findAll().stream()
                    .filter(c -> c.getRole().equals("MANAGER") && c.getProjects().contains(projectId))
                    .findFirst();
            return Optional.ofNullable(manager.map(u -> modelMapper.map(u, ManagerDto.class)).orElse(null));
    }

    }

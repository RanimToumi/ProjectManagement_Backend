package com.example.gestionPrjt.Services;

import com.example.gestionPrjt.Dto.ProjectDto;
import com.example.gestionPrjt.Dto.TaskDto;
import com.example.gestionPrjt.Models.*;
import com.example.gestionPrjt.Repo.CollaboratorRepository;
import com.example.gestionPrjt.Repo.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final ModelMapper modelMapper;
    private final TaskRepository taskRepo;
    private  final CollaboratorRepository collaboratorRepository;


    public Optional<TaskDto> findTaskById(Long taskId) {
        if (taskId == 0) {
            log.error("TaskId is null");
            return Optional.empty();
        }
        else {
            Optional<Task> task = taskRepo.findById(taskId);
            return task.map(t -> modelMapper.map(t, TaskDto.class));
        }
    }

    public List<TaskDto> findAllTasks() {
        List<Task> tasks = taskRepo.findAll();
        return tasks.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public TaskDto updateTask(TaskDto taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);
        Task savedTask = taskRepo.save(task);
        return modelMapper.map(savedTask, TaskDto.class);
    }

    public String getCollaboratorNameForTask(Long taskId) {
        Optional<Task> task = taskRepo.findById(taskId);

        if (task.isPresent()) {
            Collaborator collaborator = task.get().getCollaborator();

            if (collaborator != null) {
                return collaborator.getNom()+" "+ collaborator.getPrenom();
            } else {
                return "Aucun collaborateur associé à cette tâche.";
            }
        } else {
            return "Tâche non trouvée.";
        }
    }

    public TaskDto addTask(TaskDto taskDto) {
        // Map the TaskDto to Task
        Task task = modelMapper.map(taskDto, Task.class);


        if (taskDto.getId_collaborator() != null) {
            Optional<Collaborator> collaboratorOptional = collaboratorRepository.findById(taskDto.getId_collaborator());
            collaboratorOptional.ifPresent(collaborator -> task.setCollaborator(collaborator));
        }

        // Save the Task
        Task savedTask = taskRepo.save(task);
        

        // Map the saved Task back to TaskDto and return
        return modelMapper.map(savedTask, TaskDto.class);
    }


//    public TaskDto addTask(TaskDto taskDto) {
//
//        Task task = modelMapper.map(taskDto, Task.class);
//
//        if (taskDto.getId_collaborator() != null) {
//
//            Collaborator collaborator = new Collaborator();
//            collaborator.setId(taskDto.getId_collaborator());
//            task.setCollaborator(collaborator);
//        }
//        // Save the Task
//        Task savedTask = taskRepo.save(task);
//        // Map the saved Task back to TaskDto and return
//        return modelMapper.map(savedTask, TaskDto.class);
//    }

    public void deleteTaskById(Long taskId) {
        if (taskId.equals(0)) {
            log.error("TaskId is null");
        }
        else
            taskRepo.deleteById(taskId);
    }
    public int NbTasksCompleted(){
        List<Task> tasks= taskRepo.findAll();
        int n =tasks.stream().filter(task -> task.getStatus()==TaskStatus.Complete).toList().size();
        return  n ;
    }

    private TaskDto convertToDto(Task task) {
       TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        if (task.getCollaborator() != null) {
            taskDto.setId_collaborator(task.getCollaborator().getId());
        }
        return taskDto;
    }



}

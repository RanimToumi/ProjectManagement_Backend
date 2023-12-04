package com.example.gestionPrjt.Controllers;

import com.example.gestionPrjt.Dto.TaskDto;
import com.example.gestionPrjt.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/getTask/{taskId}")
    @PreAuthorize("hasRole('MANAGER','ADMIN')")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
        return taskService.findTaskById(taskId)
                .map(taskDto -> new ResponseEntity<>(taskDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAllTasks")
    //@PreAuthorize("hasRole('MANAGER','ADMIN')")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.findAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/getCollaboratorNameForTask/{taskId}")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> getCollaboratorNameForTask(@PathVariable("taskId") Long taskId) {
        String collaboratorName = taskService.getCollaboratorNameForTask(taskId);
        return new ResponseEntity<>(collaboratorName, HttpStatus.OK);
    }


    @PostMapping("/addTask")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) {
        TaskDto addedTask = taskService.addTask(taskDto);
        return new ResponseEntity<>(addedTask, HttpStatus.CREATED);
    }

    @PutMapping("/update/{taskId}")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        if (!taskService.findTaskById(taskId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskDto.setId(taskId);
        TaskDto updatedTask = taskService.updateTask(taskDto);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("delete/{taskId}")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        if (!taskService.findTaskById(taskId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            taskService.deleteTaskById(taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @GetMapping("/NbTasksCompleted")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Integer> NbTasksCompleted() {
        Integer n= taskService.NbTasksCompleted();
        return new ResponseEntity<Integer>(n, HttpStatus.OK);
    }


}

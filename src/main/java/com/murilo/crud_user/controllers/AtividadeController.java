package com.murilo.crud_user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.murilo.crud_user.dtos.TaskRecordDto;
import com.murilo.crud_user.models.TaskModel;
import com.murilo.crud_user.models.UserModel;
import com.murilo.crud_user.repositories.TaskRepository;
import com.murilo.crud_user.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TaskModel> createTask(@RequestBody @Valid TaskRecordDto taskDto) {
        TaskModel taskModel = new TaskModel();
        
        taskModel.setTarefa(taskDto.tarefa());
        taskModel.setDescricao(taskDto.descricao());
        taskModel.setStatus(taskDto.status());
        taskModel.setPrioridade(taskDto.prioridade());

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> getTaskById(@PathVariable UUID id) {
        Optional<TaskModel> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> updateTask(@PathVariable UUID id, @RequestBody @Valid TaskRecordDto taskDto) {
        Optional<TaskModel> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            TaskModel taskModel = taskOptional.get();
            taskModel.setTarefa(taskDto.tarefa());
            taskModel.setDescricao(taskDto.descricao());
            taskModel.setStatus(taskDto.status());
            taskModel.setPrioridade(taskDto.prioridade());

            return ResponseEntity.ok(taskRepository.save(taskModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        Optional<TaskModel> task = taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskModel>> getTasksByUserId(@PathVariable UUID userId) {
        List<TaskModel> tasks = taskRepository.findByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}/prioritize")
    public ResponseEntity<TaskModel> prioritizeTask(@PathVariable UUID id, @RequestParam String prioridade) {
        Optional<TaskModel> task = taskRepository.findById(id);
        if (task.isPresent()) {
            TaskModel taskModel = task.get();
            taskModel.setPrioridade(prioridade);
            return ResponseEntity.ok(taskRepository.save(taskModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
package com.todo.demo.controller;

import com.todo.demo.model.Task;
import com.todo.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //fetch task from external API
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getTasksFromAPI() {
        try {
            List<Task> tasks = taskService.fetchTasksFromExternalAPI();
            if (tasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //fetch task from external API and store it into database
    @GetMapping("/fetch-and-store")
    public ResponseEntity<String> fetchAndStoreTasks() {
        try {
            String response = taskService.fetchAndStoreTasksFromExternalAPI();
            if (response == null || response.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch and store tasks.");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching and storing tasks.");
        }
    }

    //Get all stored tasks from database
    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks(){
        try {
            List<Task> tasks = taskService.getAll();
            if (tasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Get task by ID to retrieve task details from the database.
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    //Update task details using an ID.
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task updated = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(updated);
    }
}

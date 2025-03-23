package com.todo.demo.controller;

import com.todo.demo.model.Task;
import com.todo.demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //fetch task from external API
    @Operation(
            summary = "Fetch tasks from external API",
            description = "Retrieve a list of tasks from an external API.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched tasks"),
                    @ApiResponse(responseCode = "204", description = "No content, no tasks found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
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
    @Operation(
            summary = "Fetch and store tasks",
            description = "Fetch tasks from an external API and store them in the database.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks successfully fetched and stored"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
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
    @Operation(
            summary = "Get all tasks",
            description = "Retrieve all tasks that are stored in the database.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched tasks"),
                    @ApiResponse(responseCode = "204", description = "No content, no tasks found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
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

    //Implement pagination for fetching stored tasks.
    @Operation(
            summary = "Get paginated tasks",
            description = "Fetch tasks from the database with pagination support.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/paginated")
    public ResponseEntity<Page<Task>> getPaginatedTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getAllTasks(page, size));
    }

    //Get task by ID to retrieve task details from the database.
    @Operation(
            summary = "Get task by ID",
            description = "Retrieve a task from the database by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched the task"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    //Update task details using an ID.
    @Operation(
            summary = "Update task details",
            description = "Update the details of an existing task by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the task"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task updated = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(updated);
    }

    //Delete a task from the database using an ID.
    @Operation(
            summary = "Delete a task",
            description = "Delete a task from the database using its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted the task"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    //Get grouped tasks by completion status to retrieve tasks grouped into completed and pending categories
    @Operation(
            summary = "Get grouped tasks by completion status",
            description = "Retrieve tasks grouped into completed and pending categories based on their completion status.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully grouped tasks by completion status"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/grouped")
    public Map<Boolean, List<Task>> getGroupedTasks() {
        return taskService.getGroupedTasksByCompletionStatus();
    }
}

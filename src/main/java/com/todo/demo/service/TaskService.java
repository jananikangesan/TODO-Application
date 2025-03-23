package com.todo.demo.service;


import com.todo.demo.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import com.todo.demo.model.Task;
import com.todo.demo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class TaskService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    private final String API_URL = "https://jsonplaceholder.typicode.com/todos";

    //fetch task from external API
    public List<Task> fetchTasksFromExternalAPI() {
        try {
            Task[] tasks = restTemplate.getForObject(API_URL, Task[].class);
            if (tasks != null && tasks.length > 0) {
                return Arrays.asList(tasks);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error fetching tasks from external API: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    //fetch task from external API and store it into database
    public String fetchAndStoreTasksFromExternalAPI() {

        try {
            Task[] tasks = restTemplate.getForObject(API_URL, Task[].class);
            if (tasks != null && tasks.length > 0) {
                List<Task> taskList = Arrays.asList(tasks);

                for (Task task : taskList) {
                    task.setCreatedAt(LocalDateTime.now());
                    taskRepository.save(task);
                }

                return "Tasks stored successfully.";
            }
            return "No tasks found in the external API.";
        } catch (Exception e) {
            System.err.println("Error fetching tasks from external API: " + e.getMessage());
            return "Failed to fetch and store tasks.";
        }
    }

    //Get all stored tasks from database
    public List<Task> getAll() {
        try {
            List<Task> tasks = taskRepository.findAll();
            return tasks.isEmpty() ? Collections.emptyList() : tasks;
        } catch (Exception e) {
            System.err.println("Error retrieving tasks from database: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    //Get task by ID to retrieve task details from the database.
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
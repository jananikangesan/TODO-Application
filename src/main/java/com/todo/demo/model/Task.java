package com.todo.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
public class Task {

    @Id
    private Long id;

    private Long userId;
    private String title;
    private Boolean completed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
package com.summary.todos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.summary.todos.model.Todo;
import com.summary.todos.service.TodoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        if (todo.getText() == null || todo.getText().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(todoService.addTodo(todo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable UUID id, @RequestBody Todo todo) {
        if (todo.getText() == null || todo.getText().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return todoService.updateTodo(id, todo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeTodos() {
        try {
            return ResponseEntity.ok(todoService.summarizeTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to summarize or send to Slack: " + e.getMessage());
        }
    }
}
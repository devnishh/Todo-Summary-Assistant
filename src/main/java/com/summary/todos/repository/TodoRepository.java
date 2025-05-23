package com.summary.todos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summary.todos.model.Todo;

import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
}
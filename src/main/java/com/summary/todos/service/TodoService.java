package com.summary.todos.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summary.todos.client.CohereClient;
import com.summary.todos.client.SlackClient;
import com.summary.todos.model.Todo;
import com.summary.todos.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private CohereClient cohereClient;

    @Autowired
    private SlackClient slackClient;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> updateTodo(UUID id, Todo todo) {
        return todoRepository.findById(id)
                .map(existing -> {
                    existing.setText(todo.getText());
                    return todoRepository.save(existing);
                });
    }

    public void deleteTodo(UUID id) {
        todoRepository.deleteById(id);
    }

    public String summarizeTodos() throws Exception {
        List<Todo> todos = todoRepository.findAll();
        if (todos.isEmpty()) {
            throw new IllegalStateException("No todos to summarize");
        }
        String todoText = todos.stream().map(Todo::getText).collect(Collectors.joining("\n"));
        String summary = cohereClient.summarize(todoText);
        slackClient.sendSummary(summary);
        return "Summary sent to Slack";
    }
}
package com.geniushyeon.architecture.controller;

import com.geniushyeon.architecture.service.TodoService;
import com.geniushyeon.architecture.swagger.TodoSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
public class TodoController implements TodoSwagger {

    private final TodoService todoService;

    @GetMapping
    public String showTodos() {
        return "hello";
    }
}

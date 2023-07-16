package com.geniushyeon.architecture.controller;

import com.geniushyeon.architecture.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;
}

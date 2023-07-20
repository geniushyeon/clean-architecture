package com.geniushyeon.architecture.swagger;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todo", description = "Todo API")
public interface TodoSwagger {

    String showTodos();
}

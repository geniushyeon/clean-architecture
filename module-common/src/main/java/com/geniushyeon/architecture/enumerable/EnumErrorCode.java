package com.geniushyeon.architecture.enumerable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumErrorCode {

    SUCCESS(0, "SUCCESS");

    private final int result;
    private final String message;
}

package com.example.keepmock.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomException extends Exception{

    private ExceptionState state;

    public CustomException(ExceptionState state) {
        this.state = state;
    }
}

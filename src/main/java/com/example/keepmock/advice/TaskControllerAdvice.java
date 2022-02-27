package com.example.keepmock.advice;

import com.example.keepmock.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TaskControllerAdvice {

    @ExceptionHandler(value = {CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiException handleError(CustomException e){
        return new ApiException(e.getState().name(), e.getState().getDescription());
    }
}

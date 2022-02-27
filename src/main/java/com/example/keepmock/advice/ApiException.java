package com.example.keepmock.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException {

    private String key;
    private String value;
}

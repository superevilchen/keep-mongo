package com.example.keepmock.utils;

import com.example.keepmock.exceptions.CustomException;

import java.util.List;
import java.util.function.Supplier;

import static com.example.keepmock.exceptions.ExceptionState.INVALID_FIELD;

public class ValidationUtils {

    public static void validate(boolean condition, Supplier<CustomException> callback) throws CustomException {
        if (!condition) {
            throw callback.get();
        }
    }

    public static void validateMany(List<Boolean> conditions, Supplier<CustomException> callback) throws CustomException {
        for (Boolean isTrue : conditions){
            if (!isTrue){
                throw callback.get();
            }
        }
    }

    public static void validateField(String field) throws CustomException {
        validate(field.equals("isArchived") || field.equals("isDiscarded"), () -> new CustomException(INVALID_FIELD));
    }
}

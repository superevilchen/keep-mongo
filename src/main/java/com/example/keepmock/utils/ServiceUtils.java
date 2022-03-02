package com.example.keepmock.utils;

import com.example.keepmock.exceptions.CustomException;

import java.util.List;
import java.util.function.Supplier;

public class ServiceUtils {

    public static void validate(boolean isExists, Supplier<CustomException> callback) throws CustomException {
        if (!isExists) {
            throw callback.get();
        }
    }

    public static void validateMany(List<Boolean> isExists, Supplier<CustomException> callback) throws CustomException {
        for (Boolean b : isExists){
            if (!b){
                throw callback.get();
            }
        }
    }
}

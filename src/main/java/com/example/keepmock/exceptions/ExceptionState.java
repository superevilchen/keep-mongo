package com.example.keepmock.exceptions;

public enum ExceptionState {

    NOT_FOUND("couldn't get what you're looking for"),
    ALREADY_EXISTS("you're attempting to override something"),
    LOGIN_FAILED("Couldn't login"),
    INVALID_DATE("invalid date......."),
    INVALID_TEXT("invalid text......");

    // TODO - more states, change descriptions

    private String description;

    ExceptionState(String description) {
        this.description = description;
    }
}

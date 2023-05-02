package ru.practicum.ewm.controller.pub.error.exception;

public class BadRequestException extends RuntimeException {


    public BadRequestException(String message) {
        super(message);
    }
}

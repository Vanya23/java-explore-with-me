package ru.practicum.ewm.controller.pub.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.controller.pub.error.exception.BadRequestException;
import ru.practicum.ewm.controller.pub.error.exception.ConflictException;
import ru.practicum.ewm.controller.pub.error.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.ewm.controller.pub.MyPatternTime.myPatternTime;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    private final String myTextError = "%s";


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ApiError handleBadRequestException(final BadRequestException e) {
        ApiError apiError = new ApiError(
                "", "",
                "", HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(myPatternTime)));

        return apiError;
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
//    public ApiError handleIMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
//        log.warn("Ошибка {}", e.getMessage(), e);
//        return new ApiError(
//                String.format(myTextError, e.getMessage())
//        );
//    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ApiError handleNotFoundException(final NotFoundException e) {
        ApiError apiError = new ApiError(
                "", "",
                "", HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(myPatternTime)));
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public ApiError handleEntityNotFoundException(final ConflictException e) {
        ApiError apiError = new ApiError(
                "", "",
                "", HttpStatus.CONFLICT.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(myPatternTime)));
        return apiError;
    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 — если возникло исключение.
//    public ApiError handleIAnyException(final Exception e) {
//        log.warn("Ошибка {}", e.getMessage(), e);
//        return new ApiError(
//                String.format(myTextError, e.getMessage())
//        );
//    }
}

package com.silvershadow.urlshortener.web;

import com.silvershadow.urlshortener.web.exception.InvalidRequestException;
import com.silvershadow.urlshortener.web.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(NotFoundException exception) {

    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBadRequest(InvalidRequestException exception) {
        return new ModelAndView();
    }
}

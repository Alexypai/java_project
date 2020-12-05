package com.myaudiolibary.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

public class ThymGlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}

package com.myaudiolibary.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.BAD_REQUEST);
        return modelAndView;
    }

    @ExceptionHandler(ConflictException.class)
    public ModelAndView handleConflictException(ConflictException e){
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.CONFLICT);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.CONFLICT);
        return modelAndView;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);
        return modelAndView;
    }


}

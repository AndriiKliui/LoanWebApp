package com.finanse.exception;

import com.google.common.base.Throwables;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {

    /**
     * Handle exceptions thrown by handlers.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("error/general");
        modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
        return modelAndView;
    }
}
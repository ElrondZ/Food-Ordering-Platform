package com.example.reggie.common;


/*
Globe exception
1. when create a new employee account with an existing userID, it handles here.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

// 拦截所有带annotation的controller
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody // need to return JSON data
@Slf4j // log info
public class GlobeExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry !")) {
            String[] ls = ex.getMessage().split(" ");
            String msg = ls[2]+" already exists !";
            return R.error(msg);
        }
        return R.error("Unknown Error");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex) {
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }
}

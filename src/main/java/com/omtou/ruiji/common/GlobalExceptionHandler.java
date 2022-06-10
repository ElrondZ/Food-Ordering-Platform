package com.omtou.ruiji.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description: To handle global exception
 * @Author: Paul Zeng
 * @date: 2022-06-10 11:34
 **/

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.error(exception.getMessage());

        if (exception.getMessage().contains("Duplicate key")) {
            String[] split = exception.getMessage().split(" ");
            String msg = split[2] + "already exists.";
            return R.error(msg);
        }
        return R.error("Unknown Exception.");
    }
}

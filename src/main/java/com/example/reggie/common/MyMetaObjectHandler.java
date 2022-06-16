package com.example.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@Slf4j

public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("Public field filling[insert] >>>>");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

        // since we cannot get HTTPSession to get userID
        // ThreadLocal class will work here
        metaObject.setValue("createUser", BaseContext.getCurID());
        metaObject.setValue("updateUser", BaseContext.getCurID());
     }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("Public field filling[update] >>>>");
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("Thread ID: {}", id);

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurID());
    }
}

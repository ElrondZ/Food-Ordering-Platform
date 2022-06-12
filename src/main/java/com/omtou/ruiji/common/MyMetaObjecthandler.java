package com.omtou.ruiji.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    /**
     * insert operation, autofill
     * @param metaObject
     */
    @Override

    public void insertFill(MetaObject metaObject) {
        log.info("public info autofill[insert]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * update operation, autofill
     * @param metaObject
     */

    public void updateFill(MetaObject metaObject) {
        log.info("public info autofill[insert]...");
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("Thread ID: {}");


        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

}

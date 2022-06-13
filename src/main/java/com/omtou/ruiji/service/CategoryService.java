package com.omtou.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.omtou.ruiji.entity.Category;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description:
 * @Author: Paul Zeng
 * @date: 2022-06-13 15:07
 **/
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}

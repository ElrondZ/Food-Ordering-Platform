package com.omtou.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.omtou.ruiji.entity.Category;


public interface CategoryService extends IService<Category> {
    public void remove(Long id);

}

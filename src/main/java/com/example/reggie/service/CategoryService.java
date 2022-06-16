package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.entity.Category;
import com.example.reggie.mapper.CategoryMapper;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}

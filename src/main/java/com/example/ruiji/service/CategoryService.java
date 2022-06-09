package com.example.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruiji.entity.Category;
import com.example.ruiji.entity.Employee;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}

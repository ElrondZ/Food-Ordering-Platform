package com.example.ruiji.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruiji.entity.Category;
import com.example.ruiji.entity.Dish;
import com.example.ruiji.entity.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}

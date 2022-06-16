package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.dto.DishDto;
import com.example.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    // add new dish, also insert dish-flavor
    // 2 tables: dish, dish-flavor
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id) ;

    // update 2 tables: dish, dish-flavor
    public void updateWithFlavor(DishDto dishDto);
}

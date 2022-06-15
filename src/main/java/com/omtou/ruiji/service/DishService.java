package com.omtou.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.omtou.ruiji.dto.DishDto;
import com.omtou.ruiji.entity.Dish;

public interface DishService extends IService<Dish> {
    //add dishes, insert flavor data at the same time, operate two tables
    public void saveWithFlavor(DishDto dishDto);

        //save dish info to table: dish
    public DishDto getByIdWithFlavor(Long id);
        //save flavor info to table dish_flavor
    public void updateWithFlavor(DishDto dishDto);

}

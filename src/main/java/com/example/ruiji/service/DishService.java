package com.example.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruiji.dto.DishDto;
import com.example.ruiji.entity.Dish;

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public  DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}

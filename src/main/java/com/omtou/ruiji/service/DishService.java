package com.omtou.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.omtou.ruiji.dto.DishDto;
import com.omtou.ruiji.entity.Dish;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description:
 * @Author: Paul Zeng
 * @date: 2022-06-13 16:17
 **/
public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}

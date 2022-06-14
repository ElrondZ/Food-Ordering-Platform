package com.omtou.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.omtou.ruiji.dto.DishDto;
import com.omtou.ruiji.entity.Dish;
import com.omtou.ruiji.entity.DishFlavor;
import com.omtou.ruiji.mapper.DishMapper;
import com.omtou.ruiji.service.DishFlavorService;
import com.omtou.ruiji.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description:
 * @Author: Paul Zeng
 * @date: 2022-06-13 16:19
 **/
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}

package com.example.reggie.service.implement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.dto.DishDto;
import com.example.reggie.entity.Dish;
import com.example.reggie.entity.DishFlavor;
import com.example.reggie.mapper.DishMapper;
import com.example.reggie.service.DishFlavorService;
import com.example.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServicelmpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Transactional // read and write 2 tables, error control;
    public void saveWithFlavor(DishDto dishDto) {
        //save dish info to DB/dish_table
        this.save(dishDto);

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        // save dish_flavor to DB/dish_table
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
         Dish dish = this.getById(id);
         DishDto dishDto = new DishDto();

         BeanUtils.copyProperties(dish, dishDto );

         // search dish flavor by id
         LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
         lambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
         List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
         dishDto.setFlavors(dishFlavorList);

         return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        // update basic dish properties
        this.updateById(dishDto);

        // erase old flavors
        LambdaQueryWrapper<DishFlavor > lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(lambdaQueryWrapper);

        // add new flavors
        List<DishFlavor> flavors = dishDto.getFlavors();

        Long dishId = dishDto.getId();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}

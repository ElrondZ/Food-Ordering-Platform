package com.example.ruiji.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.common.CustomException;
import com.example.ruiji.entity.Category;
import com.example.ruiji.entity.Dish;
import com.example.ruiji.entity.Setmeal;
import com.example.ruiji.mapper.CategoryMapper;
import com.example.ruiji.service.CategoryService;
import com.example.ruiji.service.DishService;
import com.example.ruiji.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.lwawt.macosx.CCustomCursor;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id){
        log.info("remove called {}", id);
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        System.out.println("dish count " + count);
        if(count > 0){
            throw new CustomException("This category has been connect to exist dish, cannot be delete");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);
        if(setmealCount > 0){
            throw new CustomException("This category has been connect to exist meal set, cannot be delete");
        }
        System.out.println("set count " + setmealCount);

        super.removeById(id);
    }
}

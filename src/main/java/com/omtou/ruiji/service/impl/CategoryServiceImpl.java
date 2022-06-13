package com.omtou.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.omtou.ruiji.common.CustomException;
import com.omtou.ruiji.entity.Category;
import com.omtou.ruiji.entity.Dish;
import com.omtou.ruiji.entity.Setmeal;
import com.omtou.ruiji.mapper.CategoryMapper;
import com.omtou.ruiji.service.CategoryService;
import com.omtou.ruiji.service.DishService;
import com.omtou.ruiji.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * delete id by category，check before deleting
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //adding searching condition, searching by category ID
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        //search if current category has linked dishes, if yes, error notice
        if(count1 > 0){
            //if yes error notice
            throw new CustomException("the current category has linked dishes, cannot be deleted");
        }

        //search if current category has linked meals, if yes, error notice
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //adding searching condition, searching by category ID
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count();
        if(count2 > 0){
            //if yes error notice
            throw new CustomException("the current category has linked meals, cannot be deleted");
        }

        //delete category
        super.removeById(id);
    }
}


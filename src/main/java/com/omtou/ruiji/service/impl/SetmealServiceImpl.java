package com.omtou.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.omtou.ruiji.common.CustomException;
import com.omtou.ruiji.dto.SetmealDto;
import com.omtou.ruiji.entity.Setmeal;
import com.omtou.ruiji.entity.SetmealDish;
import com.omtou.ruiji.mapper.SetmealMapper;
import com.omtou.ruiji.service.SetmealDishService;
import com.omtou.ruiji.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
    /**
     * add meal and save relation between meals and dishes
     * @param setmealDto
     */
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * add meal,save meal+dish
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //save meal basic info, operation: setmeal, insert
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //meal and dish (linked) info, setmeal_dish: insert
        setmealDishService.saveBatch(setmealDishes);


    }

    /**
     * delete meal
     * @param ids
     */
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //searching meal status
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if (count > 0) {
            // if can be deleted, error
            throw new CustomException("the meal status is in stock, cannot be deleted");
        }

        //if cannot be deleted, delete setmeal

        this.removeByIds(ids);
        //if cannot be deleted, delete setmeal
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);

        setmealDishService.remove(lambdaQueryWrapper);
        //setmealDishService


    }

}

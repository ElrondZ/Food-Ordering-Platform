package com.omtou.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.omtou.ruiji.dto.SetmealDto;
import com.omtou.ruiji.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * add meal and save relation between meals and dishes
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);



}

package com.omtou.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.omtou.ruiji.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description:
 * @Author: Paul Zeng
 * @date: 2022-06-13 16:16
 **/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}

package com.example.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruiji.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}

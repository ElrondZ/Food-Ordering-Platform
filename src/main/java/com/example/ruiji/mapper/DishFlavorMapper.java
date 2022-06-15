package com.example.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruiji.entity.DishFlavor;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}

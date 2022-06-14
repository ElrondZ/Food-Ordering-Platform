package com.omtou.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.omtou.ruiji.entity.DishFlavor;
import com.omtou.ruiji.mapper.DishFlavorMapper;
import com.omtou.ruiji.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl <DishFlavorMapper, DishFlavor> implements DishFlavorService {
}

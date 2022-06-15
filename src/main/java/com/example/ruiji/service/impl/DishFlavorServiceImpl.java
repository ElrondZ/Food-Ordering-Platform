package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.entity.DishFlavor;
import com.example.ruiji.mapper.DishFlavorMapper;
import com.example.ruiji.service.DishFlavorService;
import com.example.ruiji.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}

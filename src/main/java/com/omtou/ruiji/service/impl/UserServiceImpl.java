package com.omtou.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.omtou.ruiji.entity.User;
import com.omtou.ruiji.mapper.UserMapper;
import com.omtou.ruiji.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

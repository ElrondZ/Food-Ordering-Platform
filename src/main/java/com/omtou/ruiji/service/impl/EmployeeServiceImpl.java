package com.omtou.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.omtou.ruiji.entity.Employee;
import com.omtou.ruiji.mapper.EmployeeMapper;
import com.omtou.ruiji.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description:
 * @Author: Paul Zeng
 * @date: 2022-06-09 10:54
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

package com.omtou.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.omtou.ruiji.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
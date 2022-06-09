package com.omtou.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.omtou.ruiji.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description:
 * @Author: Paul Zeng
 * @date: 2022-06-09 10:31
 **/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}

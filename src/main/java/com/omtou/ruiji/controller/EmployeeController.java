package com.omtou.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.omtou.ruiji.common.R;
import com.omtou.ruiji.entity.Employee;
import com.omtou.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description:
 * @Author: Paul Zeng
 * @date: 2022-06-09 10:55
 **/
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Employee Login Function
     * @param request
     * @param employee
     * @return R
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        // 1. Encrypt password with md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. Query database by username
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 3. Check query
        if (emp == null) {
            return R.error("Login failed! -- Item not found.");
        }

        // 4. Check password
        if (!emp.getPassword().equals(password)) {
            return R.error("Login failed! -- Password incorrect.");
        }

        // 5. Check employee state
        if (emp.getStatus() == 0) {
            return R.error("Account blocked.");
        }

        // 6. Login success, put employee id into Session
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * Employee Logout Function
     * @param request
     * @param employee
     * @return R
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("Logout successfully!");
    }
}

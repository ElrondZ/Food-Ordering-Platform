package com.omtou.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.omtou.ruiji.common.R;
import com.omtou.ruiji.entity.Employee;
import com.omtou.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
     * Employee log out function
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("Logout successfully!");
    }

    /**
     * Add employee information
     * @param httpServletRequest
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("Add information successfully.");
    }

    /**
     * Employee information pages
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);

        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * Update employee's information via ID
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        Long empId = (Long)request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);

        return R.success("Information update successfully.");
    }

    /**
     * Search for employee's information via ID
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("Can not find this employee.");
    }
}

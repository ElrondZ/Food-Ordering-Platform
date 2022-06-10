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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * employee login
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {


        //1、password md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、check username in database
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //3、No username: fail to log in
        if (emp == null) {
            return R.error("fail to log in");
        }
        //4、check password, fail to log in if wrong
        if (!emp.getPassword().equals(password)) {
            return R.error("fail to log in");
        }
        //5、check employee status, fail if banned
        if (emp.getStatus() == 0) {
            return R.error("This account is banned");
        }
        //6、success to log in
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * employee logout
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //clean up current employee id in Session
        request.getSession().removeAttribute("employee");
        return R.success("success to log out");


    }

    /**
     * add new employee
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("add new employee, employee's information: {}",employee.toString());

        //set initial password to 123456, md5
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //find current user's ID
        Long empId = (Long) (request.getSession().getAttribute("employee"));
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("add successfully");
    }

    /**
     * search employee information by pages
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name) {
        log.info("page = {}, pageSize = {},name = {} ", page,pageSize,name);

        //build page constructor
        Page pageInfo = new Page(page,pageSize);

        //build Conditional constructor
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();

        //add filter conditions
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        //add ordering conditions
        queryWrapper.orderByDesc(Employee::getUpdateTime);


        //execute searching
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        Long empID = (Long)(request.getSession().getAttribute("employee"));
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empID);
        employeeService.updateById(employee);
        return R.success("successfully modified");
    }


    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("searching by employee id");
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }

}
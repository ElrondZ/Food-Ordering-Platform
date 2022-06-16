package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.common.R;
import com.example.reggie.entity.Employee;
import com.example.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /*
    employee login
    Parameter request
    Parameter employee
     @return
     */

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee )  {
        //if login successfully, store the employee id to the session,
        //so can access employee info anytime while login
        //"request" can get session

        //1、将页面提交的密码password进行md5加密处理 (now prefer to use JWT to decode)
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> quaryWrapper = new LambdaQueryWrapper<>();
        quaryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(quaryWrapper); //use getOne because all the usernames are unique in the DB

        //3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("Invalid username");
        }
        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("Invalid password");
         }
        //5、查看员工状态，如果为已禁用状态，则 返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("Banned User");
        }
        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

        /*
    employee logout
    Parameter request
     @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //1, remove the user info(id) from the current session.
        request.getSession().removeAttribute("employee");
        // removeAttribute >> backend/index.html >> remove the user info and herf to /backend/page/login/login.html
        return R.success("Successfully logout !");

    }

    @PostMapping // still process in employee page @RequestMapping("/employee")
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("New Employee info: {}", employee.toString());

        //set default password, new employee can change it after
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        // status has default value

        // get current user who create the new user
//        Long empID = (Long) request.getSession().getAttribute("employee"); // getAttribute return object, so that we need to change the type
//        employee.setCreateUser(empID);
//        employee.setUpdateUser(empID);

        employeeService.save(employee);
        return R.success("Create new employee Successfully ! ! !");
    }

    /**
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("Page = {}, PageSize = {}, Name = {}", page, pageSize, name);

        // construct page logic
        Page pageInfo = new Page(page, pageSize);

        // construct condition filter
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        // add a filter of searching name
        // like already has method to validate the param: name is null or not.
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // add a sort
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        // execute searching
        employeeService.page(pageInfo, lambdaQueryWrapper);
        // no return require, since the page class will handle them all
        // return to backend/page/member/list.html, line 144-150, this.table = res.data.records; this.count = res.data.total

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

//        Long empID = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empID);

        long id = Thread.currentThread().getId();
        log.info("Thread ID: {}", id);

        employeeService.updateById(employee);
        return R.success("Employee Info edited Successfully !");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info(">>>> Searching employee info >>>>");
        Employee emp = employeeService.getById(id);
        if (emp != null) {
            return R.success(emp);
        }
        return R.error("No corresponding employee ! ! !");
    }
}

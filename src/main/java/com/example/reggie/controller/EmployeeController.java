package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reggie.common.R;
import com.example.reggie.entity.Employee;
import com.example.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
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
}

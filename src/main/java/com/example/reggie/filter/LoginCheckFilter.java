package com.example.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.example.reggie.common.BaseContext;
import com.example.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;

/*
check whether if user already login or not, avoid accessing the index.html without login.
 */
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    // Path compare, can be also apply to Wildcard character, **.
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hsRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse hsResponse = (HttpServletResponse) servletResponse;

        //1. get current request URI
        String requestURI = hsRequest.getRequestURI();

        log.info("Intercept Access {}", requestURI);
        // url no needs to process
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/frontend/**",
                "/common/**"
        };

        //2. determine if this request needs to process
        boolean r = check(urls, requestURI);

        //3. if no needs, give access
        if (r) {
            log.info("Request * {} * no needs to process", requestURI);
            filterChain.doFilter(hsRequest, hsResponse);
            return; // function end, no needs for further.
        }

        //4. determine if current login status >> if login, give access
        if (hsRequest.getSession().getAttribute("employee") != null) {
            log.info("User * {} * already login, no needs to process", hsRequest.getSession().getAttribute("employee"));

            Long empId = (Long) hsRequest.getSession().getAttribute("employee");
            BaseContext.setCurID(empId);

            long id = Thread.currentThread().getId();
            log.info("Thread ID: {}", id);

            filterChain.doFilter(hsRequest, hsResponse);
            return; // function end, no needs for further.
        }

        log.info("User not yet login ! ! !");
        //5. if not login, return to login page.
        hsResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

        //backend request.js already has algorith to check R<T> response result, res.
        //If (res.data.code === 0 && res.data.msg === 'NOTLOGIN') 返回登录页面

//        log.info("Intercept Access from: {}", hsRequest.getRequestURI());
//        filterChain.doFilter(hsRequest, hsResponse);
    }

    /**
     * Path mather, check if this request needs to process
     * @param uriList
     * @param requestURI
     * @return
     */
    public boolean check(String[] uriList, String requestURI) {
        for (String s : uriList) {
            boolean match = PATH_MATCHER.match(s, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}

package com.omtou.ruiji.filter;

import com.alibaba.fastjson.JSON;
import com.omtou.ruiji.common.R;
import com.omtou.ruiji.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 *
 * @Project Name: Ruiji
 * @Description: Login Check Filter
 * @Author: Paul Zeng
 * @date: 2022-06-09 14:45
 **/

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. Get URI of the request
        String requestURI = request.getRequestURI();
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };


        // 2. Determine whether this request needs to be processed
        boolean check = check(requestURI, urls);

        // 3. If no processing is required, let it pass
        if (check) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4. Check login status, If already logged in, let go
        if(request.getSession().getAttribute("employee") != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. If not logged in, return the result of not logged in
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));


        log.info("Request Intercepted: {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    /**
     * Path Match Function
     * @param requestURI
     * @param urls
     * @return
     */
    public boolean check(String requestURI, String[] urls) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}

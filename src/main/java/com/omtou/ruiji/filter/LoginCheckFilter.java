package com.omtou.ruiji.filter;
import com.alibaba.fastjson.JSON;
import com.omtou.ruiji.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * check login status
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //PathMatcher
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1. get URI
        String requestURI = request.getRequestURI();
        log.info("Intercepted request: {}",requestURI);
        //define request url that does not need to be processed
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"

        };
        //2. check if the request needs to be processed
        boolean check = check(urls,requestURI);

        //3. pass without process
        if (check) {
            log.info("This request {} does not need to be processed");
            filterChain.doFilter(request,response);
            return;
        }

        //4. check login status
        if (request.getSession().getAttribute("employee") != null) {
            log.info("The user has logged in, User ID: {}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }

        log.info("The User has not logged in");

        //5. not login, then return result, response database to client end by output stream
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;




        //log.info("Intercept request: {}", request.getRequestURI());
        //filterChain.doFilter(request,response);
    }

    /**
     * PathMatch, check if the request needs to be passed
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match) {
                return true;
            }
        }
        return false;
    }

}

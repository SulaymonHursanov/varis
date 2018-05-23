package com.semi.security.filters;

import com.semi.security.authentication.JwtAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Component
public class JwtAuthFilter implements Filter {


    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie cookie = WebUtils.getCookie(request, "token");
        String token = cookie.getValue();
        if (token == null)
            token = request.getHeader(tokenHeader);
        Authentication authentication;

        if (token == null){
            authentication = new JwtAuthentication(null);
            authentication.setAuthenticated(false);
        }else {
            authentication = new JwtAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

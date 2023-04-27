package com.doormate.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(isContainPaySecurityKey(request)) {
            String loginKey = request.getHeader("token");
            LoginAuthToken loginAuthToken = new LoginAuthToken(loginKey);
            SecurityContextHolder.getContext().setAuthentication(loginAuthToken);
        }

        filterChain.doFilter(request,response);

    }


    private boolean isContainPaySecurityKey(HttpServletRequest request) {

        String loginKey = request.getHeader("test");
        return loginKey != null;
    }

}

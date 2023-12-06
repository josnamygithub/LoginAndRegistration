package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component  // singletone mode 
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private com.example.demo.util.jwtUtils jwtUtils;
    
    	@Override
    	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    			throws Exception {
    		String auth =request.getHeader("authorization");
    		if (! (request.getRequestURI().contains("login")||request.getRequestURI().contains("signup") )) {
    			jwtUtils.verify(auth);
    			}
    		// TODO Auto-generated method stub
    		return HandlerInterceptor.super.preHandle(request, response, handler);
    	}
}


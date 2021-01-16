package com.mesutemre.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.google.gson.Gson;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.service.security.TokenAuthenticationService;

public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		Authentication authentication = null;
		try {
			authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			if(authentication == null){
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				new Gson().toJson(new ErrorDetail(-1, "Erişim engellendi!"),response.getWriter());
				return ;
			}else{
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		} catch (Exception e) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			new Gson().toJson(new ErrorDetail(-9, "Hatalı token bilgisi!"),response.getWriter());
			return ;
		}
		
		
		filterChain.doFilter(request, response);
	}
	
}

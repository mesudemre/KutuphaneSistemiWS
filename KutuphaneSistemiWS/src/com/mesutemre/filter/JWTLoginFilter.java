package com.mesutemre.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesutemre.model.AccountCredentials;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.service.security.TokenAuthenticationService;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		AccountCredentials creds = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(	creds.getUsername(),
				creds.getPassword(), 
				Collections.<GrantedAuthority>emptyList());

		return getAuthenticationManager().authenticate(upat);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		UsernamePasswordAuthenticationToken userAuth=(UsernamePasswordAuthenticationToken)authResult;
		List<String> roller = new ArrayList<>();
		Iterator<GrantedAuthority> it=  userAuth.getAuthorities().iterator();
		while(it.hasNext()) {
		  roller.add(it.next().getAuthority().toString());
		}
		TokenAuthenticationService.addAuthentication(response, authResult.getName(),roller,null);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setStatusCode(-1);
		errorDetail.setStatusMessage("Kullanıcı adı ya da şifre hatalı!");
		
//		response.getWriter().write(error.toString());
		TokenAuthenticationService.addAuthentication(response, null,null,errorDetail);
	}
	
	
}

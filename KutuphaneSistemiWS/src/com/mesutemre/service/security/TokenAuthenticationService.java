package com.mesutemre.service.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.google.gson.Gson;
import com.jwt.JWTUtil;
import com.mesutemre.enums.RoleTurEnum;
import com.mesutemre.model.ErrorDetail;
import com.mesutemre.rol.model.RolModel;

public class TokenAuthenticationService {

	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";
	
	public TokenAuthenticationService() {
		
	}
	
	public static void addAuthentication(HttpServletResponse res, String username,List<String> roller,ErrorDetail errorDetail) {
		Map<String, String> claims = new HashMap<>();
		try {
		if(errorDetail == null){
			claims.put("username", username);
			if(roller != null){
				claims.put("roller",Arrays.toString(roller.toArray()).replace("[", "").replace("]",""));
			}else{
				claims.put("roller","");
			}
			
			JWTUtil jwtUtil = JWTUtil.create().setEnvironment("dev").build();
			String token = "";
			token = jwtUtil.generateTokenWithJWT(claims);
			res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
			
//			StringBuilder mes = new StringBuilder();
//			mes.append(" { ");
//			mes.append("\"statusCode\"");
//			mes.append(" : ");
//			mes.append("\"");
//			mes.append("200");
//			mes.append("\"");
//			mes.append(" , ");
//			mes.append("\"statusMessage\"");
//			mes.append(" : ");
//			mes.append("\"");
//			mes.append(token);
//			mes.append("\"");
//			mes.append(" } ");
			
			res.getOutputStream().println(token);
		}else{
			StringBuilder error = new StringBuilder();
			error.append(" { ");
			error.append("\"statusCode\"");
			error.append(" : ");
			error.append("\"");
			error.append("500");
			error.append("\"");
			error.append(" , ");
			error.append("\"statusMessage\"");
			error.append(" : ");
			error.append("\"");
			error.append(" Kullanici adi ya da sifre hatali! ");
			error.append("\"");
			error.append(" } ");
//			
			res.getOutputStream().println(error.toString());
			//res.setStatus(500);
		}
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch(IOException ex){
			ex.printStackTrace();
		}
		
		
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);	
		if (token != null) {			
			token = token.replace(TOKEN_PREFIX,"").trim();
			String user = JWTUtil.getDataFromKey(token,"username");		
			String strRoller = JWTUtil.getDataFromKey(token,"roller");
			List<RolModel> roller = new ArrayList<>();
			if(strRoller!=null) {
				String[] arryRol = strRoller.split(","); 
				for(String strRol:arryRol){
					roller.add(new RolModel(RoleTurEnum.getEnumByValue(strRol)));
				}
			}
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, roller) : null;
		}
		return null;
	}
	
}

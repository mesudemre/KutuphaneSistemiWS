package com.mesutemre.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mesutemre.filter.CORSFilter;
import com.mesutemre.filter.JWTAuthenticationFilter;
import com.mesutemre.filter.JWTLoginFilter;

@Configuration
@EnableWebSecurity
public class KutuphaneSistemiWSSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
    private DataSource dataSource;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/kitap/resim/*","/api/kullanici/kaydet",
				"/api/parametre/kitapturler","/api/kullanici/resim/*","/api/parametre/ilgialan/resim/*");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeRequests().antMatchers("/")
			.permitAll().antMatchers(HttpMethod.POST, "/login")
			.permitAll().antMatchers(HttpMethod.POST, "/api/kullanici/kaydet")
			.permitAll().antMatchers(HttpMethod.GET, "/metrics/**")	
			.permitAll().antMatchers(HttpMethod.GET, "/mappings")
			.permitAll().antMatchers(HttpMethod.GET, "/trace")
			.permitAll().anyRequest().authenticated().and()
			.addFilterBefore(new CORSFilter(),	UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),	UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication().dataSource(dataSource)
		.passwordEncoder(passwordEncoder())
		.usersByUsernameQuery("SELECT username,password,enabled from kutuphanesistemi.users WHERE username=? ")
		.authoritiesByUsernameQuery("SELECT username,role from kutuphanesistemi.user_roles where username=?");
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}

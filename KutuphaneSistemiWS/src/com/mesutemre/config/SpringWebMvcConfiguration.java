package com.mesutemre.config;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
//@EnableCaching(proxyTargetClass = true)
@EnableAspectJAutoProxy
@ComponentScan("com.mesutemre")
@EnableScheduling
@Import({
	KutuphaneSistemiDataSourceConfig.class,
	KutuphaneSistemiEmailConfig.class,
	KutuphaneSistemiWSSecurityConfig.class
})
public class SpringWebMvcConfiguration extends WebMvcConfigurerAdapter{


	@Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		commonsMultipartResolver.setMaxUploadSize(50000000);
		commonsMultipartResolver.setMaxInMemorySize(50000000);
		return commonsMultipartResolver;
	}
	
	
}

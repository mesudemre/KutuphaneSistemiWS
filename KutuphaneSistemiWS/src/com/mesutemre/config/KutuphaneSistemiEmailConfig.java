package com.mesutemre.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class KutuphaneSistemiEmailConfig {

    @Autowired
    private Environment env;
    
    @Bean
    public JavaMailSender getJavaMailSender(){
	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	
	mailSender.setHost(env.getProperty("kutuphanesistemi.mail.smtphost"));
	mailSender.setPort(Integer.parseInt(env.getProperty("kutuphanesistemi.mail.smtpport")));
	mailSender.setUsername(env.getProperty("kutuphanesistemi.mail.eposta"));
	mailSender.setPassword(env.getProperty("kutuphanesistemi.mail.sifre"));
	
	Properties props = mailSender.getJavaMailProperties();
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.debug", "true");
	props.put("mail.mime.charset", "UTF-8");
	
	mailSender.setJavaMailProperties(props);
	
	return mailSender;
    }
    
}

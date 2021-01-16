package com.mesutemre.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.mesutemre.util.KutuphaneSistemiUtil;

public class KutuphaneSistemiWSAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { KutuphaneSistemiRootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { SpringWebMvcConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.addMapping("/");
		registration.setMultipartConfig(new MultipartConfigElement(
				KutuphaneSistemiUtil.getTempFileUploadPath(), 2097152, 4194304,
				0));
	}
}

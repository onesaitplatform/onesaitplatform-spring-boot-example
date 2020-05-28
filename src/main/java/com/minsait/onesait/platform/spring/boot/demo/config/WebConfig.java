package com.minsait.onesait.platform.spring.boot.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
	private static final String WEBJARS = "/webjars/**";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern(WEBJARS)) {
			registry.addResourceHandler(WEBJARS).addResourceLocations("classpath:/META-INF/resources/webjars/")
					.resourceChain(false);

			registry.addResourceHandler(WEBJARS).addResourceLocations("/webjars/").resourceChain(false);
		}
		if (!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		}
	}

}

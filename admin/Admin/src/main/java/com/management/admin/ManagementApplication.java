package com.management.admin;

import com.management.admin.config.WebLogAspectConfiguration;
import com.management.admin.interceptor.WebMvcOperationLogInterceptor;
import com.management.admin.utils.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@EnableAsync
public class ManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
		System.out.println("启动内置tomcat服务成功!");
	}

	@Bean
	public WebLogAspectConfiguration geWebLogAspectConfiguration(){
		return new WebLogAspectConfiguration(true);
	}

	@Bean
	public WebMvcOperationLogInterceptor getWebMvcOperationLogInterceptor(){
		return new WebMvcOperationLogInterceptor(true);
	}
	@Bean
	public SpringApplicationContext getSpringApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("5MB");
		factory.setMaxRequestSize("5MB");
		return factory.createMultipartConfig();
	}
}

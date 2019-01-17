package com.management.admin;

import com.google.common.collect.ImmutableMap;
import com.management.admin.config.WebLogAspectConfiguration;
import com.management.admin.entity.template.Constant;
import com.management.admin.interceptor.DomainAuthenticationInterceptor;
import com.management.admin.utils.IdWorker;
import com.management.admin.utils.SMS.PaasSmsUtil;
import com.management.admin.utils.SpringApplicationContext;
import com.management.admin.utils.TokenUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.MultipartConfigElement;
import java.util.Map;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@EnableAsync
public class ManagementApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(ManagementApplication.class, args);
		System.out.println("启动内置tomcat服务成功!");
	}

	@Bean
	public WebLogAspectConfiguration getWebLogAspectConfiguration(){
		return new WebLogAspectConfiguration(true);
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

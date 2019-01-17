package com.management.admin.config;

import com.management.admin.interceptor.DomainAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringWebMvcConfigurationSupport extends WebMvcConfigurationSupport {
    @Bean
    public DomainAuthenticationInterceptor DomainAuthenticationInterceptor() {
        return new DomainAuthenticationInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 财务模块
        registry.addInterceptor(DomainAuthenticationInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }


}
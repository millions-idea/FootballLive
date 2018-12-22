package com.management.admin.config;

import com.management.admin.interceptor.WebMvcOperationLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    /**
     * 注入系统日志-isOpen可以控制日志系统的开闭
     * @return
     */
    @Bean
    public WebMvcOperationLogInterceptor WebMvcOperationLogInterceptor() {
        return new WebMvcOperationLogInterceptor(true);
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 财务模块
        registry.addInterceptor(WebMvcOperationLogInterceptor()).addPathPatterns("/**");
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
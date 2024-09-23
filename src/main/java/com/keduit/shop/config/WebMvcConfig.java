package com.keduit.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/ 로 들어오면 uploadPath 로 설정된 경로로 접속
        registry.addResourceHandler("/images/**")       // 접근경로
                .addResourceLocations(uploadPath);      // 실제 파일 경로
    }
}

package com.example.controller;

import com.example.inter.LoginInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginHandlerInterceptor(loginInfoRepository))
                .addPathPatterns("/api/*")
                .excludePathPatterns("/api/login", "/api/register", "/api/logoff", "/api/loginAsGuest");
    }
}

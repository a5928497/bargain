package com.yukoon.bargain.config;

import com.yukoon.bargain.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loginInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/login","/","/backend","/toregister","/register");
    }
}

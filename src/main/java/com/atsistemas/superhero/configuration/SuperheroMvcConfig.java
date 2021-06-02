package com.atsistemas.superhero.configuration;

import com.atsistemas.superhero.interceptor.MeasureTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class SuperheroMvcConfig implements WebMvcConfigurer {

    @Autowired
    private MeasureTimeInterceptor measureTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(measureTimeInterceptor).addPathPatterns("/api/**");
    }
}

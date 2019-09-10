package com.muggle.blog.config;

import com.muggle.blog.interceptor.ForeInterceptor;
import com.muggle.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }
    @Bean
    public ForeInterceptor getForeInterceptor() {
        return new ForeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(getForeInterceptor())
                .addPathPatterns("/**");
//        super.addInterceptors(registry);

    }
}
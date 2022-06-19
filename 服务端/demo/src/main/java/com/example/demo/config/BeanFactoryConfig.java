package com.example.demo.config;

import com.example.demo.interceptor.AuthorizationInterceptor;
import com.example.demo.resolver.CurrentUserArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用来提供bean
 * 可以创建到单独的类中
 */
@Configuration
public class BeanFactoryConfig {

    /**
     * 获取认证拦截器
     * @return
     */
    @Bean
    AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    /**
     * 获取参数解析器
     * @return
     */
    @Bean
    CurrentUserArgumentResolver currentUserArgumentResolver() {
        return new CurrentUserArgumentResolver();
    }
}

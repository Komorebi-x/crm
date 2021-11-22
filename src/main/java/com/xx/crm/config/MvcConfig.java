package com.xx.crm.config;

import com.xx.crm.interceptor.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName MvcConfig
 * @Description TODO
 * @Author xu
 * @Date 2021/10/23 17:11
 * @Version 1.0
 */
@Configuration//配置类
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Bean //将方法的返回值交给IOC维护（实例化）
    public NoLoginInterceptor noLoginInterceptor(){
        return new NoLoginInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要一个实现了拦截器功能的实例对象，这里使用的是noLoginInterceptor
        registry.addInterceptor(noLoginInterceptor())
                //设置需要被拦截的资源
                .addPathPatterns("/**") //默认拦截所有资源
                //设置不需要被拦截的资源
                .excludePathPatterns("/css/**","/images/**","/js/**","/lib/**","/index","/user/login");
    }
}

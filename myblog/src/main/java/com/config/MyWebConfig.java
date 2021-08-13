package com.config;

import com.myblog.filter.RemoveShiroThreadFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyWebConfig {
    @Bean
    public FilterRegistrationBean<RemoveShiroThreadFilter> filterFilterRegistrationBean(){
        RemoveShiroThreadFilter threadFilter = new RemoveShiroThreadFilter();
        FilterRegistrationBean<RemoveShiroThreadFilter> filterRegistrationBean = new FilterRegistrationBean<>(threadFilter, new ServletRegistrationBean[0]);
        filterRegistrationBean.addUrlPatterns(new String[]{"/*"});
        filterRegistrationBean.setOrder(-100);
        return filterRegistrationBean;
    }
}

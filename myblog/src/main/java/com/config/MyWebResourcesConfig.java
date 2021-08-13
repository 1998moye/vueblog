package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebResourcesConfig implements WebMvcConfigurer {
    private String headurl = "/img/head/";
    private String contenturl = "/img/content/";
    private String coverurl = "/img/cover/";
    private String locationPre = null;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("windows")){
            locationPre = "file:/d:/upload";
        }else{
            locationPre = "file:/upload";
        }
        System.out.println(locationPre);
        registry.addResourceHandler(headurl + "**").addResourceLocations(locationPre + headurl);
        registry.addResourceHandler(contenturl + "**").addResourceLocations(locationPre + contenturl);
        registry.addResourceHandler(coverurl + "**").addResourceLocations(locationPre + coverurl);
    }
}

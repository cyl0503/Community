package com.cyl.config;

import com.cyl.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author CYL
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  //@Autowired
  //private TestInterceptor testInterceptor;

  @Autowired
  private LoginTicketInterceptor loginTicketInterceptor;

  //@Autowired
  //private LoginRequiredInterceptor loginRequiredInterceptor;

  @Autowired
  private MessageInterceptor messageInterceptor;

  @Autowired
  private DataInterceptor dataInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    //registry.addInterceptor(testInterceptor)
        //.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg")
        //.addPathPatterns("/register", "/login");

    registry.addInterceptor(loginTicketInterceptor)
        .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

    //registry.addInterceptor(loginRequiredInterceptor)
        //.excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

    registry.addInterceptor(messageInterceptor)
        .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

    registry.addInterceptor(dataInterceptor)
        .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");
  }

}

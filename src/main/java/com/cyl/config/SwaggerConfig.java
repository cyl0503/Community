package com.cyl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author CYL
 *
 * 地址：http://localhost:8080/community/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket docket1(){
    return new Docket(DocumentationType.SWAGGER_2).groupName("cyl111");
  }
  @Bean
  public Docket docket2(){
    return new Docket(DocumentationType.SWAGGER_2).groupName("cyl222");
  }
  @Bean
  public Docket docket3(){
    return new Docket(DocumentationType.SWAGGER_2).groupName("cyl333");
  }
  //配置Swagger的Bean实例
  @Bean
  public Docket docket(Environment environment){
    //设置要显示的swagger环境
    Profiles profiles = Profiles.of("dev","test");
    //获取项目环境
    //通过environment.acceptsProfiles判断是否处在自己设定的环境中
    boolean flag = environment.acceptsProfiles(profiles);

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        //配置API分组
        .groupName("cyl")
        //enable 是否启用swagger false 则swagger不能在浏览器中启用
        //.enable(true)
        .enable(flag)
        .select()
        //RequestHandlerSelectors配置要扫描的接口方式
        //basePackage指定要扫描的包
        .apis(RequestHandlerSelectors.basePackage("com.cyl.controller"))
        //any()：扫描全部
        //none()：都不扫描
        //withClassAnnotation():扫描类上的注解 参数是注解的反射对象
        //.apis(RequestHandlerSelectors.withClassAnnotation(GetMapping.class))
        //withMethodAnnotation():扫描了方法上的注解
        //.apis(RequestHandlerSelectors.withMethodAnnotation(RestController.class))
        //paths():过滤什么路径
        //.paths(PathSelectors.ant("/cyl/**"))
        .build();
  }


  /**
   * 配置Swagger信息 apiInfo
   *
   * @return
   */
  private ApiInfo apiInfo(){
    //作者信息
    Contact contact = new Contact("cyl", "http://localhost:8080", "1505386118@qq.com");
    return new ApiInfo(
        "cyl SwaggerAPI文档",
        "牛客社区",
        "1.0",
        "http://localhost:8080",
        contact,
        "Apache 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0",
        new ArrayList());
  }
}

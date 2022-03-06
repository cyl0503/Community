package com.cyl.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Author CYL
 */
@Component //声明一个bean spring来统一管理
@Aspect //声明为一个切面组件
public class TestAspect {

  /**
   * 注解@Pointcut 定位切入点
   * 处理的目标 execution(* com.cyl.service.*.*(..))
   * 第一个*   代表返回值 什么返回值都行
   * 第二个.*  service包下的所有类
   * 第三个.*  泪下的所有方法 (add* 以add开头的方法)
   * (..)     所有的参数
   */
  @Pointcut("execution(* com.cyl.service.*.*(..))")
  public void pointcut() {

  }

  /**
   * 切入点前织入
   */
  @Before("pointcut()")
  public void before() {
    System.out.println("before");
  }

  /**
   * 切入点后织入
   * ("pointcut()") 以()内的为切入点
   */
  @After("pointcut()")
  public void after() {
    System.out.println("after");
  }

  /**
   * 有了返回值后织入
   */
  @AfterReturning("pointcut()")
  public void afterRetuning() {
    System.out.println("afterRetuning");
  }

  /**
   * 抛出异常后织入
   */
  @AfterThrowing("pointcut()")
  public void afterThrowing() {
    System.out.println("afterThrowing");
  }

  /**
   * 切入点前+后织入
   *
   * @param joinPoint 连接点 程序植入的部位 表示在程序中明确定义的点
   * @return
   * @throws Throwable
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    //处理的目标组件前的逻辑
    System.out.println("around before");
    Object obj = joinPoint.proceed();
    //处理的目标组件后的逻辑
    System.out.println("around after");
    return obj;
  }
}

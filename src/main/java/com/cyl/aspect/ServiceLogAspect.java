package com.cyl.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author CYL
 */
@Component
@Aspect
public class ServiceLogAspect {

  private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

  /**
   * 切点
   */
  @Pointcut("execution(* com.cyl.service.*.*(..))")
  public void pointcut() {

  }

  /**
   *
   * @param joinPoint 连接点
   */
  @Before("pointcut()")
  public void before(JoinPoint joinPoint) {
    // 用户[1.2.3.4](ip地址 因为有可能没有登陆),在[xxx](时间),访问了[com.cyl.service.xxx()].
    //获取IP地址
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes == null) {
      return;
    }
    HttpServletRequest request = attributes.getRequest();
    String ip = request.getRemoteHost();
    //日期
    String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    //获取哪个方法
    String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    //打印日志
    logger.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));
  }

}

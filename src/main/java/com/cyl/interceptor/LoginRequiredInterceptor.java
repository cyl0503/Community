package com.cyl.interceptor;

import com.cyl.annotation.LoginRequired;
import com.cyl.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author CYL
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

  @Autowired
  private HostHolder hostHolder;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //判断拦截对象是否为method
    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = handlerMethod.getMethod();
      //取这个注解
      LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
      //加了@LoginRequired注解但是没有登陆
      if (loginRequired != null && hostHolder.getUser() == null) {
        //重定向到登陆界面
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
      }
    }
    return true;
  }
}


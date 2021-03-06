package com.cyl.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author CYL
 */
@SuppressWarnings({"all"})
@Component
public class TestInterceptor implements HandlerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(TestInterceptor.class);

  /**
   * controller之前执行
   * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
   * 返回值：
   *  true  表示继续流程（如调用下一个拦截器或处理器）；
   *  false 表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，
   *        此时我们需要通过response来产生响应；
   *
   * @param request
   * @param response
   * @param handler
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    logger.debug("preHandle:"+handler.toString());
    return true;
  }

  /**
   *
   */

  /**
   * controller之后执行
   * 后处理回调方法，实现处理器的后处理（但在渲染视图之前）
   * 此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理
   * modelAndView也可能为null
   *
   * @param request
   * @param response
   * @param handler
   * @param modelAndView
   * @throws Exception
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    logger.debug("postHandle:"+handler.toString());
  }

  /**
   * 在templateEngine之后执行
   * 整个请求处理完毕回调方法，即在视图渲染完毕时回调
   * 如性能监控中我们可以在此记录结束时间并输出消耗时间
   * 还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中
   *
   * @param request
   * @param response
   * @param handler
   * @param ex
   * @throws Exception
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    logger.debug("afterCompletion:"+handler.toString());
  }
}

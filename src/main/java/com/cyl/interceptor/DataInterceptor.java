package com.cyl.interceptor;

import com.cyl.entity.User;
import com.cyl.service.impl.DataService;
import com.cyl.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author CYL
 */
@Component
public class DataInterceptor implements HandlerInterceptor {

  @Autowired
  private DataService dataService;

  @Autowired
  private HostHolder hostHolder;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //统计UV
    String ip = request.getRemoteHost();
    dataService.recordUV(ip);

    //统计DAU
    User user = hostHolder.getUser();
    if (user != null){
      dataService.recordDAU(user.getId());
    }
    return true;
  }
}

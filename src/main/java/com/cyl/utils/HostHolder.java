package com.cyl.utils;

import com.cyl.entity.User;
import org.springframework.stereotype.Component;


/**
 * @Author CYL
 * 持有用户信息,用于代替session对象.
 */
@Component
public class HostHolder {

  /**
   * ThreadLocal 以线程为key
   */
  private ThreadLocal<User> users = new ThreadLocal<>();

  public void setUser(User user) {
    users.set(user);
  }

  public User getUser() {
    return users.get();
  }

  public void clear() {
    users.remove();
  }

}

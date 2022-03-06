package com.cyl.controller;

import com.cyl.utils.CommunityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author CYL
 */
@Controller
@RequestMapping(value="/test")
public class TestController {



  //Cookie
  @RequestMapping(path = "/cookie/set",method = RequestMethod.GET)
  @ResponseBody
  public String setCookie(HttpServletResponse response){
    //创建cookie
    Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
    //设置cookie生效范围
    cookie.setPath("/community/test");
    //设置cookie生存时间 否则再内存中关机则无 单位：s
    cookie.setMaxAge(60*10);
    //放到response
    response.addCookie(cookie);

    return "set Cookie";
  }

  @RequestMapping(path = "/cookie/get",method = RequestMethod.GET)
  @ResponseBody
  public String getCookie(@CookieValue("code")String code) {
    System.out.println(code);
  return "get cookie";
  }


  //session
  @RequestMapping(path = "/session/set",method = RequestMethod.GET)
  @ResponseBody
  public String setSession(HttpSession session){
    session.setAttribute("id",1);
    session.setAttribute("name","rachel");
    return "set Session";
  }

  @RequestMapping(path = "/session/get",method = RequestMethod.GET)
  @ResponseBody
  public String getSession(HttpSession session){
    System.out.println(session.getAttribute("id"));
    System.out.println(session.getAttribute("name"));
    return "get Session";
  }

  // ajax示例
  @RequestMapping(path = "/ajax", method = RequestMethod.POST)
  @ResponseBody
  public String testAjax(String name, int age) {
    System.out.println(name);
    System.out.println(age);
    return CommunityUtil.getJSONString(0, "操作成功!");
  }
}

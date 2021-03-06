package com.cyl.controller;

import com.cyl.entity.DiscussPost;
import com.cyl.entity.User;
import com.cyl.service.impl.DiscussPostService;
import com.cyl.service.impl.LikeService;
import com.cyl.service.impl.UserService;
import com.cyl.utils.CommunityConstant;
import com.cyl.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class HomeController implements CommunityConstant {

  @Autowired
  private DiscussPostService discussPostService;

  @Autowired
  private UserService userService;

  @Autowired
  private LikeService likeService;

  /**
   * 访问首页
   *
   * @param model
   * @param page
   * @return
   */
  @RequestMapping(path = "/index",method = RequestMethod.GET)
  public String getIndexPage(Model model, Page page,
                             @RequestParam(name = "orderMode",defaultValue = "0")int orderMode) throws ExecutionException {
    // 方法调用前,SpringMVC会自动实例化Model和Page,并将Page注入Model.
    // 所以,在thymeleaf中可以直接访问Page对象中的数据.

    //获取帖子总行数
    page.setRows(discussPostService.findDiscussPostRows(0));
    //设置地址
    page.setPath("/index?orderMode="+orderMode);

    //获取一页的帖子列表 userId为0 查的全部帖子
    List<DiscussPost> list = discussPostService
        .findDiscussPosts(0,page.getOffset(), page.getLimit(),orderMode);

    List<Map<String,Object>> discussPosts = new ArrayList<>();
    if(list != null){
      for (DiscussPost post : list){
        Map<String,Object> map = new HashMap<>();
        map.put("post",post);
        User user = userService.findUserById(post.getUserId());
        map.put("user",user);

        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
        map.put("likeCount", likeCount);

        discussPosts.add(map);
      }
    }
    model.addAttribute("discussPosts",discussPosts);
    model.addAttribute("orderMode",orderMode);
    return "/index";
  }

  @RequestMapping(path = "/error", method = RequestMethod.GET)
  public String getErrorPage() {
    return "/error/500";
  }

  /**
   * 拒绝访问时的提示页面
   *
   * @return
   */
  @RequestMapping(path = "/denied", method = RequestMethod.GET)
  public String getDeniedPage() {
    return "/error/404";
  }
}

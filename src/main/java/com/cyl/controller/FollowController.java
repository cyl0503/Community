package com.cyl.controller;

import com.cyl.entity.Event;
import com.cyl.entity.User;
import com.cyl.event.EventProducer;
import com.cyl.service.impl.FollowService;
import com.cyl.service.impl.UserService;
import com.cyl.utils.CommunityConstant;
import com.cyl.utils.CommunityUtil;
import com.cyl.utils.HostHolder;
import com.cyl.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author CYL
 */
@Api("关注控制层")
@Controller
public class FollowController implements CommunityConstant {

  @Autowired
  private FollowService followService;

  @Autowired
  private HostHolder hostHolder;

  @Autowired
  private UserService userService;

  @Autowired
  private EventProducer eventProducer;

  @ApiOperation("关注")
  @RequestMapping(path = "/follow", method = RequestMethod.POST)
  @ResponseBody
  public String follow(@ApiParam("实体类型") int entityType,@ApiParam("实体ID") int entityId) {
    User user = hostHolder.getUser();

    followService.follow(user.getId(), entityType, entityId);

    // 触发关注事件
    Event event = new Event()
        .setTopic(TOPIC_FOLLOW)
        .setUserId(hostHolder.getUser().getId())
        .setEntityType(entityType)
        .setEntityId(entityId)
        .setEntityUserId(entityId);
    eventProducer.fireEvent(event);

    return CommunityUtil.getJSONString(0, "已关注!");
  }

  @ApiOperation("取消关注")
  @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
  @ResponseBody
  public String unfollow(@ApiParam("实体类型") int entityType,@ApiParam("实体ID") int entityId) {
    User user = hostHolder.getUser();

    followService.unfollow(user.getId(), entityType, entityId);

    return CommunityUtil.getJSONString(0, "已取消关注!");
  }

  @ApiOperation("获取关注列表")
  @RequestMapping(path = "/followees/{userId}", method = RequestMethod.GET)
  public String getFollowees(@ApiParam("用户ID") @PathVariable("userId") int userId,@ApiParam("分页实体") Page page,@ApiParam("model") Model model) {
    User user = userService.findUserById(userId);
    if (user == null) {
      throw new RuntimeException("该用户不存在!");
    }
    model.addAttribute("user", user);

    page.setLimit(5);
    page.setPath("/followees/" + userId);
    page.setRows((int) followService.findFolloweeCount(userId, ENTITY_TYPE_USER));

    List<Map<String, Object>> userList = followService.findFollowees(userId, page.getOffset(), page.getLimit());
    if (userList != null) {
      for (Map<String, Object> map : userList) {
        User u = (User) map.get("user");
        map.put("hasFollowed", hasFollowed(u.getId()));
      }
    }
    model.addAttribute("users", userList);

    return "/site/followee";
  }

  @RequestMapping(path = "/followers/{userId}", method = RequestMethod.GET)
  public String getFollowers(@PathVariable("userId") int userId, Page page, Model model) {
    User user = userService.findUserById(userId);
    if (user == null) {
      throw new RuntimeException("该用户不存在!");
    }
    model.addAttribute("user", user);

    page.setLimit(5);
    page.setPath("/followers/" + userId);
    page.setRows((int) followService.findFollowerCount(ENTITY_TYPE_USER, userId));

    List<Map<String, Object>> userList = followService.findFollowers(userId, page.getOffset(), page.getLimit());
    if (userList != null) {
      for (Map<String, Object> map : userList) {
        User u = (User) map.get("user");
        map.put("hasFollowed", hasFollowed(u.getId()));
      }
    }
    model.addAttribute("users", userList);

    return "/site/follower";
  }

  private boolean hasFollowed(int userId) {
    if (hostHolder.getUser() == null) {
      return false;
    }

    return followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
  }

}


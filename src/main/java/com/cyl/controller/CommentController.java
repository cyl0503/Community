package com.cyl.controller;

import com.cyl.entity.Comment;
import com.cyl.entity.DiscussPost;
import com.cyl.entity.Event;
import com.cyl.event.EventProducer;
import com.cyl.service.impl.CommentService;
import com.cyl.service.impl.DiscussPostService;
import com.cyl.utils.CommunityConstant;
import com.cyl.utils.HostHolder;
import com.cyl.utils.RedisKeyUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @Author CYL
 */

@Api(tags = "评论控制层")
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

  @Autowired
  private CommentService commentService;

  @Autowired
  private HostHolder hostHolder;

  @Autowired
  private EventProducer eventProducer;

  @Autowired
  private DiscussPostService discussPostService;

  @Autowired
  private RedisTemplate redisTemplate;

  @ApiOperation(value = "addComment",notes = "根据帖子id添加评论")
  @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
  public String addComment(@PathVariable("discussPostId") int discussPostId,@RequestBody Comment comment) {
    comment.setUserId(hostHolder.getUser().getId());
    comment.setStatus(0);
    comment.setCreateTime(new Date());
    commentService.addComment(comment);

    // 触发评论事件
    Event event = new Event()
        .setTopic(TOPIC_COMMENT)
        .setUserId(hostHolder.getUser().getId())
        .setEntityType(comment.getEntityType())
        .setEntityId(comment.getEntityId())
        .setData("postId", discussPostId);
    if (comment.getEntityType() == ENTITY_TYPE_POST) {
      DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
      event.setEntityUserId(target.getUserId());
    } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
      Comment target = commentService.findCommentById(comment.getEntityId());
      event.setEntityUserId(target.getUserId());
    }
    eventProducer.fireEvent(event);

    //触发发帖事件
    if(comment.getEntityType() == ENTITY_TYPE_POST){
      Event event1 = new Event()
          .setTopic(TOPIC_PUBLISH)
          .setUserId(comment.getId())
          .setEntityType(ENTITY_TYPE_POST)
          .setEntityId(discussPostId);

      eventProducer.fireEvent(event1);

      // 计算帖子分数
      String redisKey = RedisKeyUtil.getPostScoreKey();
      redisTemplate.opsForSet().add(redisKey, discussPostId);
    }

    return "redirect:/discuss/detail/" + discussPostId;
  }

}

package com.cyl.service.impl;

import com.cyl.dao.DiscussPostMapper;
import com.cyl.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {

  @Autowired
  DiscussPostMapper discussPostMapper;

  public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit){
    return discussPostMapper.selectDiscussPosts(userId, offset, limit);
  }

  public int findDiscussPostRows(int userId){
    return discussPostMapper.selectDiscussPostRows(userId);
  }
}

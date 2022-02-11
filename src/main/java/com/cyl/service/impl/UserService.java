package com.cyl.service.impl;

import com.cyl.dao.UserMapper;
import com.cyl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserMapper userMapper;

  public User findUserById(int id){
    return userMapper.selectById(id);
  }
}

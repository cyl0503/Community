package com.cyl.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author CYL
 */
@Data
public class LoginTicket {

  /**
   * 自增ID
   */
  private int id;
  /**
   * 用户ID
   */
  private int userId;
  /**
   * 登陆凭证
   */
  private String ticket;
  /**
   * 0 有效 1 无效 退出后失效
   */
  private int status;
  /**
   * 过期时间
   */
  private Date expired;

}

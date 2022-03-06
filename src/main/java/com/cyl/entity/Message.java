package com.cyl.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author CYL
 */
@Data
public class Message {

  /**
   * 自增ID
   */
  private int id;
  /**
   * 发送人
   */
  private int fromId;
  /**
   * 接收人
   */
  private int toId;
  /**
   * 会话ID
   */
  private String conversationId;
  /**
   * 内容
   */
  private String content;
  /**
   * 0-未读;1-已读;2-删除;
   */
  private int status;
  /**
   * 创建时间
   */
  private Date createTime;

}

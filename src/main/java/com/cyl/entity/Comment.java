package com.cyl.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * @Author CYL
 */
@Data
@ApiModel("评论实体")
public class Comment {

  /**
   * 评论自增ID
   */
  @ApiModelProperty(name = "id", value = "评论自增ID")
  private int id;
  /**
   *  评论的用户ID
   */
  @ApiModelProperty(name = "id", value = "评论的用户ID",required = true)
  private int userId;
  /**
   *  实体类型 1 帖子 2 回复
   */
  @ApiModelProperty(name = "id", value = "实体类型 1 帖子 2 回复",required = true)
  private int entityType;
  /**
   *  实体ID
   */
  @ApiModelProperty(name = "id", value = "实体ID",required = true)
  private int entityId;
  /**
   *  回复的对象ID
   */
  @ApiModelProperty(name = "id", value = "回复的对象ID",required = true)
  private int targetId;
  /**
   * 内容
   */
  @ApiModelProperty(name = "id", value = "内容",required = true)
  private String content;
  /**
   *  评论状态 0 正常 1 已删除
   */
  @ApiModelProperty(name = "id", value = "评论状态 0 正常 1 已删除",required = true)
  private int status;
  /**
   *  创建时间
   */
  @ApiModelProperty(name = "id", value = "创建时间",required = true)
  private Date createTime;
}

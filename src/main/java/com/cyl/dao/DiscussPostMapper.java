package com.cyl.dao;

import com.cyl.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

  /*
  * 查询帖子
  *
  * userId 用户ID
  * 分页：
  * offset 起始行号
  * limit  每页条数
  * */
  List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);

  /*
   * 查询帖子行数
   *
   * userId 用户ID
   *
   * @Param注解用于给参数取别名
   * 如果只有一个参数，并且在if里使用，必须加别名
   * */
  int selectDiscussPostRows(@Param("userId") int userId);

}

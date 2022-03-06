package com.cyl.dao;

import com.cyl.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DiscussPostMapper {

  /*
  * 查询帖子
  *
  * userId 用户ID
  * 分页：
  * offset 起始行号
  * limit  每页条数
  * */

  List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit,int orderMode);

  /**
   * 查询帖子行数
   *
   * //@Param注解用于给参数取别名
   * 如果只有一个参数，并且在if里使用，必须加别名
   *
   * @param userId 用户ID
   * @return
   */
  int selectDiscussPostRows(@Param("userId") int userId);

  int insertDiscussPost(DiscussPost discussPost);

  DiscussPost selectDiscussPostById(int id);

  int updateCommentCount(int id, int commentCount);

  int updateType(int id,int type);

  int updateStatus(int id,int status);

  int updateScore(int id, double score);

}

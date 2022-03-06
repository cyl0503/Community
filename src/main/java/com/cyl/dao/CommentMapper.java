package com.cyl.dao;

import com.cyl.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author CYL
 */
@Mapper
public interface CommentMapper {

  List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

  int selectCountByEntity(int entityType, int entityId);

  int insertComment(Comment comment);

  Comment selectCommentById(int id);
}
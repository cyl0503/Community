package com.cyl.service.impl;

import com.cyl.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @Author CYL
 */
@Service
public class LikeService {

  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 点赞功能 第一次点赞 第二次取消赞
   * @param userId 点赞的用户ID
   * @param entityType 实体类型
   * @param entityId 实体ID
   * @param entityUserId 被点赞用户的ID
   */
  public void like(int userId, int entityType, int entityId, int entityUserId) {
    /*String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
    boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
    if (isMember) {
      redisTemplate.opsForSet().remove(entityLikeKey, userId);
    } else {
      redisTemplate.opsForSet().add(entityLikeKey, userId);
    }*/

    //redis 编程式事务
    // 一个业务有两个更新操作 (点赞 + 维护用户总点赞数)
    redisTemplate.execute(new SessionCallback() {
      @Override
      public Object execute(RedisOperations operations) throws DataAccessException {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);

        //查询放到事务外
        boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

        operations.multi();

        if (isMember) {
          operations.opsForSet().remove(entityLikeKey, userId);
          operations.opsForValue().decrement(userLikeKey);
        } else {
          operations.opsForSet().add(entityLikeKey, userId);
          operations.opsForValue().increment(userLikeKey);
        }

        return operations.exec();
      }
    });
  }

  /**
   * 查询某实体点赞的数量
   *
   * @param entityType 实体类型
   * @param entityId 实体ID
   * @return 某实体点赞的数量
   */
  public long findEntityLikeCount(int entityType, int entityId) {
    String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
    return redisTemplate.opsForSet().size(entityLikeKey);
  }

  /**
   * 查询某人对某实体的点赞状态
   *
   * @param userId 用户ID
   * @param entityType 实体类型
   * @param entityId 实体ID
   * @return 点赞状态 1 点赞 2 未点赞
   */
  public int findEntityLikeStatus(int userId, int entityType, int entityId) {
    String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
    return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
  }

  /**
   * 查询某个用户获得的赞
   *
   * @param userId
   * @return
   */
  public int findUserLikeCount(int userId) {
    String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
    //默认Object类型
    Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
    //转换成基本数据类型
    return count == null ? 0 : count;
  }
}

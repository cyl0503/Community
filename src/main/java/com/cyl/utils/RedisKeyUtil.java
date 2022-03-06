package com.cyl.utils;

/**
 * @Author CYL
 */
public class RedisKeyUtil {

  /**
   * 粉丝
   */
  private static final String SPLIT = ":";
  /**
   * 点赞实体
   */
  private static final String PREFIX_ENTITY_LIKE = "like:entity";
  /**
   * 用户点赞量
   */
  private static final String PREFIX_USER_LIKE = "like:user";
  /**
   * 关注
   */
  private static final String PREFIX_FOLLOWEE = "followee";
  /**
   * 粉丝
   */
  private static final String PREFIX_FOLLOWER = "follower";
  /**
   * 验证码
   */
  private static final String PREFIX_KAPTCHA = "kaptcha";
  /**
   * 登录凭证
   */
  private static final String PREFIX_TICKET = "ticket";
  /**
   * 用户
   */
  private static final String PREFIX_USER = "user";
  /**
   * UV 独立访客
   */
  private static final String PREFIX_UV = "uv";
  /**
   * DAU 日活跃用户
   */
  private static final String PREFIX_DAU = "dau";


  private static final String PREFIX_POST = "post";


  /**
   * 某个实体的赞
   * like:entity:entityType:entityId -> set(userId) 存点赞的用户ID
   *
   * @param entityType 实体类型(帖子/回复)
   * @param entityId 实体ID
   * @return 某个实体的赞的Key
   */
  public static String getEntityLikeKey(int entityType, int entityId) {
    return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
  }

  /**
   * 某个用户的赞
   * like:user:userId -> int
   *
   * @param userId 用户ID
   * @return
   */
  public static String getUserLikeKey(int userId) {
    return PREFIX_USER_LIKE + SPLIT + userId;
  }

  /**
   * 某个用户关注的实体
   * followee:userId:entityType -> zset(entityId,now)
   *
   * @param userId 用户ID
   * @param entityType 实体类型
   * @return
   */
  public static String getFolloweeKey(int userId, int entityType) {
    return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
  }

  /**
   *  某个实体拥有的粉丝
   *  follower:entityType:entityId -> zset(userId,now)
   *
   * @param entityType 实体类型
   * @param entityId 实体ID
   * @return ol
   */
  public static String getFollowerKey(int entityType, int entityId) {
    return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
  }

  /**
   * 登录验证码
   *
   * @param owner
   * @return
   */
  public static String getKaptchaKey(String owner) {
    return PREFIX_KAPTCHA + SPLIT + owner;
  }

  /**
   * 登录的凭证
   *
   * @param ticket
   * @return
   */
  public static String getTicketKey(String ticket) {
    return PREFIX_TICKET + SPLIT + ticket;
  }


  /**
   * 用户
   *
   * @param userId
   * @return
   */
  public static String getUserKey(int userId) {
    return PREFIX_USER + SPLIT + userId;
  }

  /**
   * UV
   *
   * @param date
   * @return
   */
  public static String getUVKey(String date){
    return PREFIX_UV + SPLIT + date;
  }

  /**
   * 区间UV
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static String getUVKey(String startDate,String endDate){
    return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
  }

  /**
   * 单日活跃用户
   *
   * @param date
   * @return
   */
  public static String getDAUKey(String date){
    return PREFIX_DAU + SPLIT + date;
  }

  /**
   * 区间活跃用户
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static String getDAUKey(String startDate,String endDate){
    return PREFIX_DAU + SPLIT +startDate + SPLIT + endDate;
  }

  // 帖子分数
  public static String getPostScoreKey() {
    return PREFIX_POST + SPLIT + "score";
  }
}
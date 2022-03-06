package com.cyl.service.impl;

import com.cyl.dao.DiscussPostMapper;
import com.cyl.entity.DiscussPost;
import com.cyl.utils.SensitiveFilter;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"all"})
@Service
public class DiscussPostService {

  private static final Logger logger = LoggerFactory.getLogger(DiscussPostService.class);

  @Autowired
  DiscussPostMapper discussPostMapper;

  @Autowired
  private SensitiveFilter sensitiveFilter;

  @Autowired
  private LoadingCache<String, List<DiscussPost>> postListCache;

  @Autowired
  private LoadingCache<Integer,Integer> postRowCache;

 /* @Value("${caffeine.posts.max-size}")
  private int maxSize;

  @Value("${caffeine.posts.expire-seconds}")
  private int expireSeconds;*/

  //Caffeine核心接口：Cache,LoadingCache,AsyncLoadingCache
  //LoadingCache 同步阻塞
  //AsyncLoadingCache 异步并发

  //帖子列表缓存
  //private LoadingCache<String,List<DiscussPost>> postListCache;
  //帖子总数缓存
  //private LoadingCache<Integer,Integer> postRowCache;

  /*@PostConstruct
  public void init() {
    //初始化帖子列表缓存
    postListCache = (LoadingCache<String, List<DiscussPost>>) Caffeine.newBuilder()
        .maximumSize(maxSize)
        .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
        .build(new CacheLoader<String, List<DiscussPost>>() {
          @Override
          public @Nullable List<DiscussPost> load(String key) throws Exception {
            if (key == null || key.length() == 0) {
              throw new IllegalArgumentException("参数错误!");
            }
            String[] params = key.split(":");
            int offset = Integer.valueOf(params[0]);
            int limit = Integer.valueOf(params[1]);

            //二级缓存：Redis -> mysql

            // logger.debug("load post list from DB.");
            return discussPostMapper.selectDiscussPosts(0, offset, limit, 1);
          }
        });
    //初始化帖子总数缓存
    postListCache = (LoadingCache<String, List<DiscussPost>>) Caffeine.newBuilder()
        .maximumSize(maxSize)
        .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
        .build(new CacheLoader<Integer, Integer>() {
          @Override
          public @Nullable Integer load(Integer key) throws Exception {
            //二级缓存
            //logger.debug("load post rows from DB.");
            return discussPostMapper.selectDiscussPostRows(key);
          }
        });
  }*/

  /**
   * 查帖子
   * @param userId
   * @param offset
   * @param limit
   * @param orderMode
   * @return
   */
  public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit,int orderMode) throws ExecutionException {
    if(userId == 0 && orderMode == 1){
      return postListCache.get(offset+":"+limit);
    }
    logger.debug("load post from DB.");
    return discussPostMapper.selectDiscussPosts(userId, offset, limit,orderMode);
  }

  /**
   * 查询帖子表总行数
   *
   * @param userId
   * @return
   */
  public int findDiscussPostRows(int userId) throws ExecutionException {
    if (userId == 0){
      return postRowCache.get(userId);
    }
    logger.debug("load rows from DB.");
    return discussPostMapper.selectDiscussPostRows(userId);
  }

  public int addDiscussPost(DiscussPost post) {
    if (post == null) {
      throw new IllegalArgumentException("参数不能为空!");
    }

    // 转义HTML标记
    post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
    post.setContent(HtmlUtils.htmlEscape(post.getContent()));

    // 过滤敏感词 title content
    post.setTitle(sensitiveFilter.filter(post.getTitle()));
    post.setContent(sensitiveFilter.filter(post.getContent()));

    return discussPostMapper.insertDiscussPost(post);
  }

  public DiscussPost findDiscussPostById(int id) {
    return discussPostMapper.selectDiscussPostById(id);
  }

  public int updateCommentCount(int id, int commentCount) {
    return discussPostMapper.updateCommentCount(id, commentCount);
  }

  public int updateType(int id,int type){
    return discussPostMapper.updateType(id,type);
  }

  public int updateStatus(int id,int status){
    return discussPostMapper.updateStatus(id,status);
  }

  public int updateScore(int id, double score) {
    return discussPostMapper.updateScore(id, score);
  }
}

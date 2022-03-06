package com.cyl.config;


import com.cyl.dao.DiscussPostMapper;
import com.cyl.entity.DiscussPost;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig{

  @Autowired
  private DiscussPostMapper discussPostMapper;

  @Value("${caffeine.posts.max-size}")
  private int maxSize;

  @Value("${caffeine.posts.expire-seconds}")
  private int expireSeconds;

  /*@Bean
  public Cache<String, Object> caffeineCache() {
    return Caffeine.newBuilder()
        // 设置最后一次写入或访问后经过固定时间过期
        .expireAfterWrite(60, TimeUnit.SECONDS)
        // 初始的缓存空间大小
        .initialCapacity(100)
        // 缓存的最大条数
        .maximumSize(1000)
        .build();
  }*/

  @Bean
  public LoadingCache<String, List<DiscussPost>> postListCache() {
    return Caffeine.newBuilder()
        .maximumSize(maxSize)
        .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
        .build(new CacheLoader<String, List<DiscussPost>>() {
          @Override
          public @Nullable
          List<DiscussPost> load(String key) throws Exception {
            if (key == null || key.length() == 0) {
              throw new IllegalArgumentException("参数错误!");
            }
            String[] params = key.split(":");
            int offset = Integer.valueOf(params[0]);
            int limit = Integer.valueOf(params[1]);

            //二级缓存：Redis -> mysql

            //logger.debug("load post list from DB.");
            return discussPostMapper.selectDiscussPosts(0, offset, limit, 1);
          }
        });
  }

  @Bean
  public LoadingCache<Integer,Integer> postRowCache(){
    return Caffeine.newBuilder()
        .maximumSize(maxSize)
        .expireAfterWrite(expireSeconds,TimeUnit.SECONDS)
        .build(new CacheLoader<Integer, Integer>() {
          @Override
          public @Nullable Integer load(@NonNull Integer key) throws Exception {
            //二级缓存
            //logger.debug("load post rows from DB.");
            return discussPostMapper.selectDiscussPostRows(key);
          }
        });
  }

}
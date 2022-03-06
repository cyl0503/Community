package com.cyl.controller;

import com.cyl.entity.DiscussPost;
import com.cyl.service.impl.ElasticsearchService;
import com.cyl.service.impl.LikeService;
import com.cyl.service.impl.UserService;
import com.cyl.utils.CommunityConstant;
import com.cyl.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author CYL
 */
@Controller
public class SearchController implements CommunityConstant {

  @Autowired
  private ElasticsearchService elasticsearchService;

  @Autowired
  private UserService userService;

  @Autowired
  private LikeService likeService;

  /**
   * search?keyword=xxx
   *
   * @param keyword
   * @param page
   * @param model
   * @return
   */
  @RequestMapping(value = "/search",method = RequestMethod.GET)
  public String search(String keyword, Page page, Model model) throws IOException {
    //搜索帖子
    List<DiscussPost> searchResult = elasticsearchService.searchDiscussPost(keyword,page.getOffset(),page.getLimit());
    //聚合数据
    List<Map<String,Object>> discussPosts = new ArrayList<>();
    if (searchResult != null){
      for (DiscussPost post : searchResult) {
        Map<String,Object> map = new HashMap<>();
        //帖子
        map.put("post",post);
        //作者
        map.put("user",userService.findUserById(post.getUserId()));
        //点赞量
        map.put("likeCount",likeService.findEntityLikeCount(ENTITY_TYPE_POST,post.getId()));
        discussPosts.add(map);
      }
    }
    model.addAttribute("discussPosts",discussPosts);
    model.addAttribute("keyword",keyword);

    //分页信息
    page.setPath("/search?keyword="+keyword);
    page.setRows(searchResult == null ? 0 : searchResult.size());

    return "/site/search";
  }
}

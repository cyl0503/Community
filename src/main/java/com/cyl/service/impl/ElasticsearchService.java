package com.cyl.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.cyl.dao.elasticsearch.DiscussPostRepository;
import com.cyl.entity.DiscussPost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cyl
 */
@Service
public class ElasticsearchService {

  @Autowired
  private DiscussPostRepository discussRepository;

  @Qualifier("client")
  @Autowired
  private RestHighLevelClient restHighLevelClient;

  public void saveDiscussPost(DiscussPost post) {
    discussRepository.save(post);
  }

  public void deleteDiscussPost(int id) {
    discussRepository.deleteById(id);
  }

  public List<DiscussPost> searchDiscussPost(String keyword, int offset, int limit) throws IOException {
    SearchRequest searchRequest = new SearchRequest("discusspost");//discusspost是索引名，就是表名

    //高亮
    HighlightBuilder highlightBuilder = new HighlightBuilder();
    highlightBuilder.field("title");
    highlightBuilder.field("content");
    highlightBuilder.requireFieldMatch(false);
    highlightBuilder.preTags("<span style='color:red'>");
    highlightBuilder.postTags("</span>");
    //highlightBuilder.preTags("<em>");
    //highlightBuilder.postTags("</em>");

    //构建搜索条件
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
        .query(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
        .sort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
        .sort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
        .sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
        .from(offset)// 指定从哪条开始查询
        .size(limit)// 需要查出的总记录条数
        .highlighter(highlightBuilder);//高亮

    searchRequest.source(searchSourceBuilder);
    SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    List<DiscussPost> list = new LinkedList<>();
    for (SearchHit hit : searchResponse.getHits().getHits()) {
      DiscussPost discussPost = JSONObject.parseObject(hit.getSourceAsString(), DiscussPost.class);

      // 处理高亮显示的结果
      HighlightField titleField = hit.getHighlightFields().get("title");
      if (titleField != null) {
        discussPost.setTitle(titleField.getFragments()[0].toString());
      }
      HighlightField contentField = hit.getHighlightFields().get("content");
      if (contentField != null) {
        discussPost.setContent(contentField.getFragments()[0].toString());
      }
      System.out.println(discussPost);
      list.add(discussPost);
    }
    return list;
  }
}

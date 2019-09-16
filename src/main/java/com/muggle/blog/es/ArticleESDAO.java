package com.muggle.blog.es;

import com.muggle.blog.pojo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleESDAO extends ElasticsearchRepository<Article,Integer> {

}
package com.muggle.blog.dao;

import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleContentDAO extends JpaRepository<ArticleContent,Integer> {
    ArticleContent getByArticle(Article article);
//    List<ArticleContent> findByArticleOrderByIdDesc(Article article);
}
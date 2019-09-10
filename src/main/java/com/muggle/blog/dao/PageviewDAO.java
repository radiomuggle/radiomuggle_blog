package com.muggle.blog.dao;

import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.Pageview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageviewDAO extends JpaRepository<Pageview,Integer> {
    int countByArticle(Article article);
}
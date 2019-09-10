package com.muggle.blog.dao;

import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleImgDAO  extends JpaRepository<ArticleImg,Integer> {
        public List<ArticleImg> findByArticleAndTypeOrderByIdDesc(Article article, String type);
    }

package com.muggle.blog.dao;

import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDAO  extends JpaRepository<Review,Integer> {
    Page<Review> findByArticleOrderByIdDesc(Article article, Pageable pageable);
    List<Review> findByArticleOrderByIdDesc(Article article);
    int countByArticle(Article article);
}

package com.muggle.blog.dao;

import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleDAO extends JpaRepository<Article,Integer> {
    List<Article> findByCategoryOrderById(Category category);
    Page<Article> findByCategoryOrderByIdDesc(Category category, Pageable pageable);
    int countByCategory(Category category);
    Article findTopByOrderByIdDesc();

    //
//    Page<Article> findAll(Pageable pageable);
    }

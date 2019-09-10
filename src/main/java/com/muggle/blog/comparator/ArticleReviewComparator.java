package com.muggle.blog.comparator;

import com.muggle.blog.pojo.Article;

import java.util.Comparator;

public class ArticleReviewComparator implements Comparator<Article> {

    @Override
    public int compare(Article p1, Article p2) {
        return p2.getReviewCount()-p1.getReviewCount();
    }

}

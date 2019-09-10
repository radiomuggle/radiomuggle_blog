package com.muggle.blog.comparator;

import com.muggle.blog.pojo.Article;

import java.util.Comparator;

public class ArticlePageviewComparator implements Comparator<Article> {

    @Override
    public int compare(Article p1, Article p2) {
        System.out.println("viewcount: "+(p2.getViewCount()-p1.getViewCount()));
        return p2.getViewCount()-p1.getViewCount();
    }

}
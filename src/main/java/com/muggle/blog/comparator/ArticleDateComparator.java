package com.muggle.blog.comparator;

import com.muggle.blog.pojo.Article;

import java.util.Comparator;

public class ArticleDateComparator implements Comparator<Article> {

    @Override
    public int compare(Article a1, Article a2) {
//        System.out.println("create_time: "+a1.getCreate_time().compareTo(a2.getCreate_time()));
        return a2.getCreate_time().compareTo(a1.getCreate_time());
    }

}
package com.muggle.blog.controller;

import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleContent;
import com.muggle.blog.service.ArticleContentService;
import com.muggle.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ArticleContentController {
    @Autowired
    ArticleContentService articleContentService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/articles/{aid}/articleContent")
    public ArticleContent get(@PathVariable("aid") int aid) throws Exception {
        Article article = articleService.get(aid);
        articleContentService.init(article);
        ArticleContent articleContent = articleContentService.getByArticle(article);
//        System.out.println(article);
//        articleContent.setArticle(article);
//        articleContent.setModify_time(new Date());
//        articleContentService.update(articleContent);
//        ArticleContent bean= articleContentService.getByArticle(article);
        return articleContent;
    }

    @PutMapping("/articleContent")
    public Object update(@RequestBody ArticleContent bean) throws Exception {
        bean.setModify_time(new Date());
        articleContentService.update(bean);
        return bean;
    }


}
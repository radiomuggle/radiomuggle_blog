package com.muggle.blog.controller;

import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleContent;
import com.muggle.blog.service.ArticleContentService;
import com.muggle.blog.service.ArticleImgService;
import com.muggle.blog.service.ArticleService;
import com.muggle.blog.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleImgService articleImgService;
    @Autowired
    ArticleContentService articleContentService;

    @GetMapping("/articles")
    public Page4Navigator<Article> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Article> page = articleService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        articleImgService.setFirstArticleImgs(page.getContent());
        articleService.setReviewAndPageviewNumber(page.getContent());
        return page;
    }
    @GetMapping("/articles_all")
    public Page4Navigator<Article> list_all(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "6") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Article> page = articleService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        articleImgService.setFirstArticleImgs(page.getContent());
//        articleService.setReviewAndPageviewNumber(page.getContent());
        return page;
    }

    @GetMapping("/categories/{cid}/articles")
    public Page4Navigator<Article> listByCategory(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "6") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Article> page = articleService.listByCategory(cid, start, size, 5);
        articleImgService.setFirstArticleImgs(page.getContent());
//        System.out.println("sort1: "+sort);
//        if(null!=sort) {
//            System.out.println("sort: "+sort);
//            switch (sort) {
//
//                case "review":
//                    Collections.sort(page.getContent(), new ArticleReviewComparator());
//                    break;
//                case "date":
//                    Collections.sort(page.getContent(), new ArticleDateComparator());
//                    break;
//                case "pageview":
//                    Collections.sort(page.getContent(), new ArticlePageviewComparator());
//                    break;
//            }
//        }
        return page;
    }


    @PostMapping("/articles")
    public Object add(@RequestBody Article bean) throws Exception {
        bean.setCreate_time(new Date());
        bean.setModify_time(new Date());
        articleService.add(bean);
        articleContentService.add(bean);
//        System.out.println("id: "+bean.getId());
        return bean;
    }

    @DeleteMapping("/articles/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        articleService.delete(id);
        return null;
    }
    @GetMapping("/articles/{id}")
    public Article get(@PathVariable("id") int id) throws Exception {
        Article bean=articleService.get(id);
        bean.setContent(articleContentService.getByArticle(bean).getContent());
//        System.out.println(bean);
//        articleService.setReviewAndPageviewNumber(bean);
        return bean;
    }

    @PutMapping("/articles")
    public Object update(@RequestBody Article bean) throws Exception {
        bean.setModify_time(new Date());
        articleService.update(bean);
        ArticleContent articleContent = articleContentService.getByArticle(bean);
        articleContent.setContent(bean.getContent());
        articleContentService.update(articleContent);
        return bean;
    }
}

package com.muggle.blog.service;

import com.muggle.blog.dao.ArticleContentDAO;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleContent;
import com.muggle.blog.util.Markdown2HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@CacheConfig(cacheNames="articleContents")
public class ArticleContentService {
    @Autowired
    ArticleContentDAO articleContentDAO;

    @Autowired ArticleImgService articleImgService;
    @Autowired
    PageviewService pageviewService;
//    @Autowired ArticleService articleService;

    @Autowired
    ReviewService reviewService;

    public void init(Article article) {
        ArticleContent articleContent = getByArticle(article);
        if(null==articleContent){
            articleContent = new ArticleContent();
            articleContent.setArticle(article);
            articleContent.setContent("默认内容");
            articleContent.setModify_time(new Date());
            articleContentDAO.save(articleContent);
        }
    }

    @CachePut(key="'articleContent-one-'+ #p0")
    public void add(Article article) {
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticle(article);
        articleContent.setContent(article.getContent());
        articleContent.setModify_time(new Date());
        articleContentDAO.save(articleContent);
    }

    @CacheEvict(allEntries=true)
    public void update(ArticleContent bean) {
        articleContentDAO.save(bean);
    }

    @Cacheable(key="'articleContent-aid'+ #p0.id")
    public ArticleContent getByArticle(Article article) {
        return articleContentDAO.getByArticle(article);
    }

    public void fill(Article article) {
        articleImgService.setFirstArticleImg(article);
        ArticleContent ac = getByArticle(article);
        ac.setContent(Markdown2HtmlUtil.markdown2html(ac.getContent()));
        article.setArticleContent(ac);
        article.setReviews(reviewService.list(article));
        article.setReviewCount(reviewService.getCount(article));
        article.setViewCount(pageviewService.getCount(article));
    }

    @CacheEvict(allEntries=true)
    public void delete(int id) {
        articleContentDAO.delete(id);
    }
}

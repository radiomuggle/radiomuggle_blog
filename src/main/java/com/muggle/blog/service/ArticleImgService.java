package com.muggle.blog.service;

import com.muggle.blog.dao.ArticleImgDAO;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames="articleImgs")
public class ArticleImgService   {

    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired
    ArticleImgDAO articleImgDAO;
    @Autowired ArticleService articleService;

    @CacheEvict(allEntries=true)
//    @CachePut(key="'articleImg-one-'+ #p0")
    public void add(ArticleImg bean) {
        articleImgDAO.save(bean);

    }

    @CacheEvict(allEntries=true)
//    @CacheEvict(key="'articleImg-one-'+ #p0")
    public void delete(int id) {
        articleImgDAO.delete(id);
    }

    @Cacheable(key="'articleImgs-one'+ #p0")
    public ArticleImg get(int id) {
        return articleImgDAO.findOne(id);
    }

    @Cacheable(key="'articleSingleImgs-aid-'+ #p0.id")
    public List<ArticleImg> listSingleArticleImgs(Article article) {
        return articleImgDAO.findByArticleAndTypeOrderByIdDesc(article, type_single);
    }
    @Cacheable(key="'articleDetailImgs-aid-'+ #p0.id")
    public List<ArticleImg> listDetailArticleImgs(Article article) {
        return articleImgDAO.findByArticleAndTypeOrderByIdDesc(article, type_detail);
    }

    public void setFirstArticleImg(Article article) {
        List<ArticleImg> singleImgs = listSingleArticleImgs(article);
        if(!singleImgs.isEmpty())
            article.setFirstArticleImg(singleImgs.get(0));
        else
            article.setFirstArticleImg(new ArticleImg()); //这样做是考虑到还没有来得及设置图片，但是在后台管理里查看图片。

    }
    public void setFirstArticleImgs(List<Article> articles) {
        for (Article article : articles)
            setFirstArticleImg(article);
    }

}
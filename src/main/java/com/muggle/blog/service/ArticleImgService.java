package com.muggle.blog.service;

import com.muggle.blog.dao.ArticleImgDAO;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleImgService   {

    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired
    ArticleImgDAO articleImgDAO;
    @Autowired ArticleService articleService;

    public void add(ArticleImg bean) {
        articleImgDAO.save(bean);

    }
    public void delete(int id) {
        articleImgDAO.delete(id);
    }

    public ArticleImg get(int id) {
        return articleImgDAO.findOne(id);
    }

    public List<ArticleImg> listSingleArticleImgs(Article article) {
        return articleImgDAO.findByArticleAndTypeOrderByIdDesc(article, type_single);
    }
    public List<ArticleImg> listDetailArticleImgs(Article article) {
        return articleImgDAO.findByArticleAndTypeOrderByIdDesc(article, type_detail);
    }

    public void setFirstArticleImg(Article article) {
        List<ArticleImg> singleImgs = listSingleArticleImgs(article);
        if(!singleImgs.isEmpty())
            article.setFirstArticleImg(singleImgs.get(0));
        else
            article.setFirstArticleImg(new ArticleImg()); //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。

    }
    public void setFirstArticleImgs(List<Article> articles) {
        for (Article article : articles)
            setFirstArticleImg(article);
    }

}
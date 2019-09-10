package com.muggle.blog.service;

import com.muggle.blog.dao.PageviewDAO;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.Pageview;
import com.muggle.blog.util.Page4Navigator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageviewService {
    @Autowired
    PageviewDAO pageviewDAO;
    @Autowired ArticleService articleService;


    public Pageview get(int id) {
        return pageviewDAO.findOne(id);
    }

    public void add(Pageview bean) {
        String url = bean.getUrl();

//        System.out.println(bean.toString());


        if(StringUtils.startsWith(url, "fore_article/")) {
           int aid = Integer.parseInt(StringUtils.remove(url, "fore_article/"));
            bean.setArticle(articleService.get(aid));
        }
        pageviewDAO.save(bean);

    }

    public Page4Navigator<Pageview> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =pageviewDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public List<Pageview> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return pageviewDAO.findAll(sort);
    }

    public void delete(int id) {
        pageviewDAO.delete(id);
    }

    public int getCount(Article article) {
        return pageviewDAO.countByArticle(article);
    }

}

package com.muggle.blog.service;

import com.muggle.blog.dao.ReviewDAO;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.Pageview;
import com.muggle.blog.pojo.Review;
import com.muggle.blog.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames="reviews")
public class ReviewService {
    @Autowired
    ReviewDAO reviewDAO;
    @Autowired ArticleService articleService;

    @Cacheable(key="'reviews-aid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    public Page4Navigator<Review> list(int aid, int start, int size, int navigatePages) {
        Article article = articleService.get(aid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Review> pageFromJPA =reviewDAO.findByArticleOrderByIdDesc(article,pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Cacheable(key="'reviews-page-'+#p0+'-'+#p1")
    public Page4Navigator<Pageview> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =reviewDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    @Cacheable(key="'reviews-one'+ #p0")
    public Review get(int id) {
        return reviewDAO.findOne(id);
    }

    @CacheEvict(allEntries=true)
    public void update(Review bean) {
        reviewDAO.save(bean);
    }

    @CacheEvict(allEntries=true)
    public void add(Review bean) {
        reviewDAO.save(bean);
    }

    @CacheEvict(allEntries=true)
    public void delete(int id) {
        reviewDAO.delete(id);
    }

    @Cacheable(key="'reviews-aid-'+#p0.id")
    public List<Review> list(Article article){
        List<Review> result =  reviewDAO.findByArticleOrderByIdDesc(article);
        return result;
    }
    @Cacheable(key="'reviews'")
    public List<Review> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return reviewDAO.findAll(sort);
    }

    public int getCount(Article article) {
        return reviewDAO.countByArticle(article);
    }

}

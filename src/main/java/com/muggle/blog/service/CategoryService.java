package com.muggle.blog.service;

import com.muggle.blog.dao.CategoryDAO;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.Category;
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
@CacheConfig(cacheNames="categories")
public class CategoryService {
    @Autowired CategoryDAO categoryDAO;
    @Autowired ArticleService articleService;

    @Cacheable(key="'categories-page-'+#p0+ '-' + #p1")
    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =categoryDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    @Cacheable(key="'categories-all'")
    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }


    @CacheEvict(allEntries=true)

    public void add(Category bean) {
        categoryDAO.save(bean);
    }

    @CacheEvict(allEntries=true)
    public void delete(int id) {
        removeCategoryFromArticle(categoryDAO.findOne(id));
        categoryDAO.delete(id);
    }

    @Cacheable(key="'categories-one-'+ #p0")
    public Category get(int id) {
        Category c= categoryDAO.findOne(id);
        return c;
    }

    @CacheEvict(allEntries=true)
    public void update(Category bean) {
        categoryDAO.save(bean);
    }

    public void removeCategoryFromArticle(List<Category> cs) {
        for (Category category : cs) {
            removeCategoryFromArticle(category);
        }
    }

    public void removeCategoryFromArticle(Category category) {
        articleService.fill(category);
        List<Article> articles =category.getArticles();
        if(null!=articles) {
            for (Article article : articles) {
                article.setCategory(null);
                articleService.update(article);
            }
        }
        category.setArticles(null);
    }


    public void setArticleNumber(Category category) {
        int articleCount = articleService.getCountByCategory(category);
        category.setArticleCount(articleCount);

    }

    public void setArticleNumber(List<Category> categories) {
        for (Category category : categories)
            setArticleNumber(category);
    }
}
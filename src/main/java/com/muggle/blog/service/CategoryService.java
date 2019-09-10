package com.muggle.blog.service;

import com.muggle.blog.dao.CategoryDAO;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.Category;
import com.muggle.blog.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired CategoryDAO categoryDAO;
    @Autowired ArticleService articleService;

    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =categoryDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }



    public void add(Category bean) {
        categoryDAO.save(bean);
    }

    public void delete(int id) {
        categoryDAO.delete(id);
    }
    public Category get(int id) {
        Category c= categoryDAO.findOne(id);
        return c;
    }
    public void update(Category bean) {
        categoryDAO.save(bean);
    }

    public void removeCategoryFromArticle(List<Category> cs) {
        for (Category category : cs) {
            removeCategoryFromArticle(category);
        }
    }

    public void removeCategoryFromArticle(Category category) {
        List<Article> articles =category.getArticles();
        if(null!=articles) {
            for (Article article : articles) {
                article.setCategory(null);
            }
        }
        List<List<Article>> articlesByRow =category.getArticlesByRow();
        if(null!=articlesByRow) {
            for (List<Article> as : articlesByRow) {
                for (Article a: as) {
                    a.setCategory(null);
                }
            }
        }
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
package com.muggle.blog.service;

import com.muggle.blog.dao.ArticleDAO;
import com.muggle.blog.es.ArticleESDAO;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleContent;
import com.muggle.blog.pojo.Category;
import com.muggle.blog.pojo.Review;
import com.muggle.blog.util.Page4Navigator;
import com.muggle.blog.util.SpringContextUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames="articles")
public class ArticleService  {

    @Autowired
    ArticleDAO articleDAO;
    @Autowired
    ArticleESDAO articleESDAO;
    @Autowired ReviewService reviewService;
    @Autowired PageviewService pageviewService;
    @Autowired CategoryService categoryService;
    @Autowired ArticleImgService articleImgService;

    @Cacheable(key="'articles-page-'+#p0+'-'+#p1")
    public Page4Navigator<Article> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =articleDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    @Cacheable(key="'articles-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    public Page4Navigator<Article> listByCategory(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Article> pageFromJPA =articleDAO.findByCategoryOrderByIdDesc(category,pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Cacheable(key="'articles'")
    public List<Article> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return articleDAO.findAll(sort);
    }

    public Article getNextArticle(int id) {
        Article lasta = articleDAO.findTopByOrderByIdDesc();
        id++;
        Article a = articleDAO.findOne(id);
        while(a==null && id<lasta.getId()) {
            id++;
            a = articleDAO.findOne(id);
        }
        return a;
    }
    public Article getPreArticle(int id) {
        if(id!=0) id--;
        Article a = articleDAO.findOne(id);
        while(a==null && id>0) {
            id--;
            a = articleDAO.findOne(id);
        }
        return a;
    }
    public Article getRandomArticle(int aid) {
        int lastid = articleDAO.findTopByOrderByIdDesc().getId();
        int id = (int) (Math.random()*lastid);
        Article a = articleDAO.findOne(id);
        while(a==null || id<0 || id==aid) {
            id = (int) (Math.random()*lastid);
            a = articleDAO.findOne(id);
        }
        return a;
    }

    @CacheEvict(allEntries=true)
    public void add(Article bean) {
        articleDAO.save(bean);
        articleESDAO.save(bean);
    }

    @CacheEvict(allEntries=true)
    public void delete(int id) {
        articleDAO.delete(id);
        articleESDAO.delete(id);
    }

    @Cacheable(key="'articles-one-'+ #p0")
    public Article get(int id) {
        Article c= articleDAO.findOne(id);
        return c;
    }

    @CacheEvict(allEntries=true)
    public void update(Article bean) {
        articleDAO.save(bean);
        articleESDAO.save(bean);
    }

    public int getArticleCount(Category category) {
        return articleDAO.countByCategory(category);
    }


    public void setReviewAndPageviewNumber(Article article) {
        int reviewCount = reviewService.getCount(article);
        int pageviewCount = pageviewService.getCount(article);
        article.setReviewCount(reviewCount);
        article.setViewCount(pageviewCount);

    }

    public void setReviewAndPageviewNumber(List<Article> articles) {
        for (Article article : articles)
            setReviewAndPageviewNumber(article);
    }

    public void fill(List<Category> categorys) {
        for (Category category : categorys) {
            fill(category);
        }
    }
//    public void fill(Category category) {
//        List<Article> articles = listByCategory(category);
//        articleImgService.setFirstArticleImgs(articles);
//        category.setArticles(articles);
//    }
public void fill(Category category) {
        ArticleService articleService = SpringContextUtil.getBean(ArticleService.class);
    List<Article> articles = articleService.listByCategory(category);
    articleImgService.setFirstArticleImgs(articles);
    category.setArticles(articles);
}

    public void fillByRow(List<Category> categorys) {
        int articleNumberEachRow = 8;
        for (Category category : categorys) {
            List<Article> articles =  category.getArticles();
            List<List<Article>> articlesByRow =  new ArrayList<>();
            for (int i = 0; i < articles.size(); i+=articleNumberEachRow) {
                int size = i+articleNumberEachRow;
                size= size>articles.size()?articles.size():size;
                List<Article> articlesOfEachRow =articles.subList(i, size);
                articlesByRow.add(articlesOfEachRow);
            }
            category.setArticlesByRow(articlesByRow);
        }
    }

    @Cacheable(key="'articles-cid-'+ #p0.id")
    public List<Article> listByCategory(Category category){
        return articleDAO.findByCategoryOrderById(category);
    }

    public void removeArticleFromArticleContent(Article article) {
        ArticleContent articleContent =article.getArticleContent();
        articleContent.setArticle(null);
    }
    public void removeArticleFromReview(Article article) {
        List<Review> reviews = article.getReviews();
        if(null!=reviews) {
            for (Review review : reviews) {
                review.setArticle(null);
            }
        }
    }

    public int getCountByCategory(Category category) {

        return articleDAO.countByCategory(category);
    }

    private void initDatabase2ES(){
        Pageable pageable = new PageRequest(0, 5);
        Page<Article> page = articleESDAO.findAll(pageable);
        if(page.getContent().isEmpty()){
            List<Article> articles = articleDAO.findAll();
            for (Article article : articles) {
                articleESDAO.save(article);
            }
        }
    }

    public List<Article> search(String keyword, int start, int size) {
        initDatabase2ES();
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.matchPhraseQuery("name", keyword),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                .scoreMode("sum")
                .setMinScore(10);
        Sort sort  = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(start, size,sort);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
        Page<Article> page = articleESDAO.search(searchQuery);
        return page.getContent();
    }


}
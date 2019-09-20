package com.muggle.blog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@Document(indexName = "blog",type = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
//@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="cid")
    private Category category;

     String name;

     String summary;


     int traffic;

     Date create_time;

     Date modify_time;


    @Transient
    private ArticleImg firstArticleImg;
    @Transient
    private List<ArticleImg> articleSingleImages;
    @Transient
    private List<ArticleImg> articleDetailImages;
    @Transient
    private int reviewCount;
    @Transient
    private int viewCount;

    @Transient
    private ArticleContent articleContent;
    @Transient
    private List<Review> reviews;
    @Transient
    private List<Pageview> pageviews;
    @Transient
    private List<ArticleImg> articleImgs;

    @Transient
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArticleContent getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(ArticleContent articleContent) {
        this.articleContent = articleContent;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public int getTraffic() {
        return traffic;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public ArticleImg getFirstArticleImg() {
        return firstArticleImg;
    }

    public void setFirstArticleImg(ArticleImg firstArticleImg) {
        this.firstArticleImg = firstArticleImg;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", firstArticleImg=" + firstArticleImg +
                ", articleSingleImages=" + articleSingleImages +
                ", articleDetailImages=" + articleDetailImages +
                ", reviews=" + reviews +
                ", pageviews=" + pageviews +
                ", articleImgs=" + articleImgs +
                ", content='" + content + '\'' +
                '}';
    }

    public List<ArticleImg> getArticleSingleImages() {
        return articleSingleImages;
    }

    public void setArticleSingleImages(List<ArticleImg> articleSingleImages) {
        this.articleSingleImages = articleSingleImages;
    }

    public List<ArticleImg> getArticleDetailImages() {
        return articleDetailImages;
    }

    public void setArticleDetailImages(List<ArticleImg> articleDetailImages) {
        this.articleDetailImages = articleDetailImages;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<Pageview> getPageviews() {
        return pageviews;
    }

    public void setPageviews(List<Pageview> pageviews) {
        this.pageviews = pageviews;
    }

    public List<ArticleImg> getArticleImgs() {
        return articleImgs;
    }

    public void setArticleImgs(List<ArticleImg> articleImgs) {
        this.articleImgs = articleImgs;
    }
}

package com.muggle.blog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articlecontent")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class ArticleContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "aid")
    private Article article;

    private String content;

    private Date modify_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    @Override
    public String toString() {
        return "ArticleContent{" +
                "id=" + id +
                ", article=" + article +
                ", content='" + content + '\'' +
                ", modify_time=" + modify_time +
                '}';
    }
}

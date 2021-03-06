package com.muggle.blog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pageview")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Pageview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "aid")
    private Article article;

    private Date create_time;

    private String ip;

    private String url;

    private String area_ip;

    @Transient
    private String show_ip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getArea_ip() {
        return area_ip;
    }

    public void setArea_ip(String area_ip) {
        this.area_ip = area_ip;
    }

    public String getShow_ip() {
        return show_ip;
    }

    public void setShow_ip(String show_ip) {
        this.show_ip = show_ip;
    }

    @Override
    public String toString() {
        return "Pageview{" +
                "id=" + id +
                ", article=" + article +
                ", create_time=" + create_time +
                ", ip='" + ip + '\'' +
                ", url='" + url + '\'' +
                ", area_ip='" + area_ip + '\'' +
                '}';
    }
}

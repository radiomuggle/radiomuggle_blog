package com.muggle.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForePageController {
    @GetMapping(value="/")
    public String index(){
        return "redirect:home";
    }
    @GetMapping(value="/home")
    public String home(){
        return "fore/home";
    }
    @GetMapping(value="/fore_category")
    public String category(){
        return "fore/category";
    }
    @GetMapping(value="/about")
    public String about(){
        return "fore/about";
    }
    @GetMapping(value="/fore_article")
    public String article() {
        return "fore/article";
    }

    @GetMapping(value = "/article_all")
    public String article_all() {

        return "fore/article_all";
    }

    @GetMapping(value="/search")
    public String searchResult(){
        return "fore/search";
    }

/**
 * 注册
 */
//    @GetMapping(value = "/muggle_register")
//    public String register() {
//        return "fore/register";
//    }


}
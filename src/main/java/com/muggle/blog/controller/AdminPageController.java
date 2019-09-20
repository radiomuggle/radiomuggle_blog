package com.muggle.blog.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    @GetMapping(value="/admin")
    public String admin(){
        return "redirect:admin_index";
    }
    @GetMapping(value="/admin_index")
    public String index(){
        return "admin/index";
    }
    @GetMapping(value="/admin_category_list")
    public String listCategory(){
        return "admin/listCategory";
    }
    @GetMapping(value="/admin_category_edit")
    public String editCategory(){
        return "admin/editCategory";

    }

    @GetMapping(value="/admin_article_list")
    public String listArticle(){
        return "admin/listArticle";

    }

    @GetMapping(value="/admin_article_edit")
    public String editArticle(){
        return "admin/editArticle";

    }
    @GetMapping(value="/admin_articleImg_list")
    public String listArticleImg(){
        return "admin/listArticleImg";

    }

    @GetMapping(value="/admin_articleContent_edit")
    public String editArticleContent(){
        return "admin/editArticleContent";

    }
    @GetMapping(value="/admin_review_edit")
    public String editReview(){
        return "admin/editReview";

    }

    @GetMapping(value="/admin_review_list")
    public String listReview(){
        return "admin/listReview";

    }

    @GetMapping(value="/admin_pageview_list")
    public String listPageview(){
        return "admin/listPageview";

    }

    @GetMapping(value="/login")
    public String login(){
        return "admin/login";
    }

//    @GetMapping("/adminLogout")
//    public String logout(HttpSession session) {
//        session.removeAttribute("user");
//        return "redirect:home";
//    }
@GetMapping("/adminLogout")
public String logout( ) {
    Subject subject = SecurityUtils.getSubject();
    if(subject.isAuthenticated())
        subject.logout();
    return "redirect:home";
}

}
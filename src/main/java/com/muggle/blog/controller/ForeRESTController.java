package com.muggle.blog.controller;

import com.muggle.blog.comparator.ArticleDateComparator;
import com.muggle.blog.comparator.ArticlePageviewComparator;
import com.muggle.blog.comparator.ArticleReviewComparator;
import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.Category;
import com.muggle.blog.pojo.User;
import com.muggle.blog.service.*;
import com.muggle.blog.util.Result;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@RestController
public class ForeRESTController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleImgService articleImgService;
    @Autowired
    ArticleContentService articleContentService;
    @Autowired
    PageviewService pageviewService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserService userService;

//    @GetMapping("fore_category/{cid}")
//    public Object category(@PathVariable("cid") int cid) {
//        Category c = categoryService.get(cid);
//        articleService.fill(c);
//        articleService.setReviewAndPageviewNumber(c.getArticles());
//        c.setArticleCount(articleService.getArticleCount(c));
//        categoryService.removeCategoryFromArticle(c);
//        return c;
//    }

@GetMapping("fore_category/{cid}")
public Object category(@PathVariable("cid") int cid,String sort) {
    Category c = categoryService.get(cid);
    articleService.fill(c);
    articleService.setReviewAndPageviewNumber(c.getArticles());
    c.setArticleCount(articleService.getArticleCount(c));
    categoryService.removeCategoryFromArticle(c);
    if(null!=sort) {
        System.out.println("sort: "+sort);
        switch (sort) {

            case "review":
                Collections.sort(c.getArticles(), new ArticleReviewComparator());
                break;
            case "date":
                Collections.sort(c.getArticles(), new ArticleDateComparator());
                break;
            case "pageview":
                Collections.sort(c.getArticles(), new ArticlePageviewComparator());
                break;
        }
    }
    return c;
}

    @GetMapping("/fore_category_list")
    public Object categories() {
        List<Category> cs= categoryService.list();
        articleService.fill(cs);
        articleService.fillByRow(cs);
        categoryService.removeCategoryFromArticle(cs);
        return cs;
    }



    @GetMapping("fore_article/{aid}")
    public Object article(@PathVariable("aid") int aid) {
        Article a = articleService.get(aid);
        articleContentService.fill(a);
        articleService.removeArticleFromArticleContent(a);
        articleService.removeArticleFromReview(a);

        return a;
    }

    @GetMapping("fore_next_article/{aid}")
    public Object next_article(@PathVariable("aid") int aid) {
        Article a = articleService.getNextArticle(aid);
        return a;
    }
    @GetMapping("fore_pre_article/{aid}")
    public Object pre_article(@PathVariable("aid") int aid) {
        Article a = articleService.getPreArticle(aid);
        return a;
    }
    @GetMapping("fore_rand_article/{aid}")
    public Object rand_article(@PathVariable("aid") int aid) {
        Article a = articleService.getRandomArticle(aid);
        return a;
    }
    @GetMapping("all_article_count")
    public Object all_article_count() {
        return articleService.list().size();
    }

    @GetMapping("all_view_count")
    public Object all_view_count() {
        return pageviewService.list().size();
    }

    @GetMapping("all_review_count")
    public Object all_review_count() {
        return reviewService.list().size();
    }

    @PostMapping("/fore_register")
    public Object register(@RequestBody User user, HttpSession session) {
        String inputImageCode = user.getInputImageCode();
        String captchaId = (String) session.getAttribute("verifyCode");
        System.out.println("验证码是：" + captchaId);
        System.out.println("用户输入的是：" + inputImageCode);
        if (!captchaId.equals(inputImageCode)) {
            System.out.println("输入错误");
            String message = "验证码错误";
            return Result.fail(message);
        } else {
            String name =  user.getName();
            String password = user.getPassword();
            name = HtmlUtils.htmlEscape(name);
            user.setName(name);

            boolean exist = userService.isExist(name);

            if(exist){
                String message ="用户名已经被使用,不能使用";
                return Result.fail(message);
            }

            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            int times = 2;
            String algorithmName = "md5";

            String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();

            user.setSalt(salt);
            user.setPassword(encodedPassword);

            userService.add(user);

            return Result.success();
        }

    }

}

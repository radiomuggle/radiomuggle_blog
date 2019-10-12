package com.muggle.blog.controller;

import com.muggle.blog.pojo.Article;
import com.muggle.blog.pojo.ArticleImg;
import com.muggle.blog.service.ArticleImgService;
import com.muggle.blog.service.ArticleService;
import com.muggle.blog.service.CategoryService;
import com.muggle.blog.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleImgController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleImgService articleImgService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/articles/{aid}/articleImgs")
    public List<ArticleImg> list(@RequestParam("type") String type, @PathVariable("aid") int aid) throws Exception {
        Article article = articleService.get(aid);
//        System.out.println(aid);
        if(ArticleImgService.type_single.equals(type)) {
            List<ArticleImg> singles =  articleImgService.listSingleArticleImgs(article);
            return singles;
        }
        else if(ArticleImgService.type_detail.equals(type)) {
            List<ArticleImg> details =  articleImgService.listDetailArticleImgs(article);
            return details;
        }
        else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/articleImgs")
    public Object add(@RequestParam("aid") int aid, @RequestParam("type") String type, MultipartFile image, HttpServletRequest request) throws Exception {
        ArticleImg bean = new ArticleImg();
        Article article = articleService.get(aid);
        bean.setArticle(article);
        bean.setType(type);

        articleImgService.add(bean);
        String folder = "img/";
        if(ArticleImgService.type_single.equals(bean.getType())){
            folder +="articleSingle";
        }
        else{
            folder +="articleDetail";
        }
        File imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        System.out.println("id为"+aid+"的文章题图添加： "+bean.getId()+".jpg");
        String fileName = file.getName();
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ArticleImgService.type_single.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/articleSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/articleSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }

        return bean;
    }

    @DeleteMapping("/articleImgs/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        ArticleImg bean = articleImgService.get(id);
        articleImgService.delete(id);

        String folder = "img/";
        if(ArticleImgService.type_single.equals(bean.getType()))
            folder +="articleSingle";
        else
            folder +="articleDetail";

        File  imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        System.out.println("题图删除： "+bean.getId()+".jpg");
        String fileName = file.getName();
        file.delete();

        if(ArticleImgService.type_single.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/articleSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/articleSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }

        return null;
    }

}
package com.muggle.blog.controller;

import com.muggle.blog.pojo.Category;
import com.muggle.blog.service.CategoryService;
import com.muggle.blog.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Category> page = categoryService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        categoryService.setArticleNumber(page.getContent());
        return page;
    }

    @GetMapping("/categories_list")
    public List<Category> list() throws Exception {
        return categoryService.list();
    }

    @PostMapping("/categories")
    public Object add(@RequestBody Category bean) throws Exception {
        categoryService.add(bean);
        return bean;
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        categoryService.delete(id);
        return null;
    }
    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        Category bean=categoryService.get(id);
        return bean;
    }

//    @PutMapping("/categories/{id}")
//    public Object update(Category bean,  HttpServletRequest request) throws Exception {
//        String name = request.getParameter("name");
//        bean.setName(name);
//        int number = Integer.parseInt(request.getParameter("number"));
//        bean.setNumber(number);
//        categoryService.update(bean);
//        return bean;
//    }
@PutMapping("/categories")
public Object update(@RequestBody Category bean) throws Exception {
    categoryService.update(bean);
    return bean;
}
}
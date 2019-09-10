package com.muggle.blog.service;

import com.muggle.blog.dao.UserDAO;
import com.muggle.blog.pojo.User;
import com.muggle.blog.util.Page4Navigator;
import com.muggle.blog.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;
    public boolean isExist(String name) {
        UserService userService = SpringContextUtil.getBean(UserService.class);
        User user = userService.getByName(name);
        return null!=user;
    }

    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =userDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public User getByName(String name) {
        return userDAO.findByName(name);
    }

    public User get(String name, String password) {

        return userDAO.getByNameAndPassword(name, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }
}
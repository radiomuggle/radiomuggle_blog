package com.muggle.blog.controller;

import com.muggle.blog.pojo.User;
import com.muggle.blog.service.UserService;
import com.muggle.blog.util.Page4Navigator;
import com.muggle.blog.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<User> page = userService.list(start,size,5);
        return page;
    }

//    @PostMapping("/adminLogin")
//    public Object login(@RequestBody User userParam, HttpSession session) {
//        String inputImageCode = userParam.getInputImageCode();
//        String captchaId = (String) session.getAttribute("verifyCode");
//        System.out.println("验证码是：" + captchaId);
//        System.out.println("用户输入的是：" + inputImageCode);
//        if (!captchaId.equals(inputImageCode)) {
//            System.out.println("输入错误");
//            String message = "验证码错误";
//            return Result.fail(message);
//        } else {
//            String name =  userParam.getName();
//            name = HtmlUtils.htmlEscape(name);
//            User user =userService.get(name,userParam.getPassword());
//            if(null==user){
//                String message ="账号密码错误";
//                return Result.fail(message);
//            }
//            else{
//                session.setAttribute("user", user);
//                return Result.success();
//            }
//        }
//    }
@PostMapping("/adminLogin")
public Object login(@RequestBody User userParam, HttpSession session) {
    String inputImageCode = userParam.getInputImageCode();
    String captchaId = (String) session.getAttribute("verifyCode");
    System.out.println("验证码是：" + captchaId);
    System.out.println("用户输入的是：" + inputImageCode);
    if (!captchaId.equals(inputImageCode)) {
        System.out.println("输入错误");
        String message = "验证码错误";
        return Result.fail(message);
    } else {
        String name =  userParam.getName();
        name = HtmlUtils.htmlEscape(name);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, userParam.getPassword());
        try {
            subject.login(token);
            User user = userService.getByName(name);
//          subject.getSession().setAttribute("user", user);
            session.setAttribute("user", user);
            return Result.success();
        } catch (AuthenticationException e) {
            String message ="账号密码错误";
            return Result.fail(message);
        }
    }
}


}
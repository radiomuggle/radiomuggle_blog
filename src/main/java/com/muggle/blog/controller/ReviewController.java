package com.muggle.blog.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.muggle.blog.pojo.Pageview;
import com.muggle.blog.pojo.Review;
import com.muggle.blog.service.ReviewService;
import com.muggle.blog.util.Page4Navigator;
import com.muggle.blog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @GetMapping("/articles/{aid}/reviews")
    public Page4Navigator<Review> list(@PathVariable("aid") int aid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Review> page =reviewService.list(aid, start, size,5);
        return page;
    }

    @GetMapping("/reviews")
    public Page4Navigator<Pageview> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Pageview> page =reviewService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    @GetMapping("/reviews/{id}")
    public Review get(@PathVariable("id") int id) throws Exception {
        Review bean=reviewService.get(id);
        return bean;
    }

    @PostMapping("/reviews")
    public Object add(@RequestBody Review bean) throws Exception {
        bean.setCreate_time(new Date());
        reviewService.add(bean);
        return bean;
    }

    @DeleteMapping("/reviews/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        reviewService.delete(id);
        return null;
    }

    @PutMapping("/reviews")
    public Object update(@RequestBody Review bean) throws Exception {
        Review review = reviewService.get(bean.getId());
        review.setIs_effective(bean.getIs_effective());
        reviewService.update(review);
        return review;
    }

//    @GetMapping("/images/imagecode")
//    public String imagecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        OutputStream os = response.getOutputStream();
//        Map<String,Object> map = ImageCode.getImageCode(60, 20, os);
//        String simpleCaptcha = "simpleCaptcha";
//        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
//        request.getSession().setAttribute("codeTime",new Date().getTime());
//        try {
//            ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
//        } catch (IOException e) {
//            return "";
//        }
//        return null;
//    }
//
//    @PostMapping("/checkcode")
//    @ResponseBody
//    public String checkcode(HttpServletRequest request, HttpSession session) throws Exception {
//        String checkCode = request.getParameter("checkCode");
//        Object cko = session.getAttribute("simpleCaptcha") ; //验证码对象
//        if(cko == null){
//            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
//            return "验证码已失效，请重新输入！";
//        }
//        String captcha = cko.toString();
//        Date now = new Date();
//        Long codeTime = Long.valueOf(session.getAttribute("codeTime")+"");
//        if(StringUtils.isEmpty(checkCode) || captcha == null || !(checkCode.equalsIgnoreCase(captcha))) {
//            request.setAttribute("errorMsg", "验证码错误！");
//            return "验证码错误！";
//        } else if ((now.getTime()-codeTime)/1000/60>5) {
//            //验证码有效时长为5分钟
//            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
//            return "验证码已失效，请重新输入！";
//        }else {
//            session.removeAttribute("simpleCaptcha");
//            return "1";
//        }
//    }


    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute("verifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    //    @PostMapping(value ="/verifyCode", consumes = {"application/x-www-form-urlencoded"})
//    public Object imgverifyControllerDefaultKaptcha(@RequestBody String inputImageCode, HttpSession session) {
//        String captchaId = (String) session.getAttribute("verifyCode");
//        System.out.println("验证码是：" + captchaId);
//        System.out.println("用户输入的是：" + inputImageCode);
//        inputImageCode = inputImageCode.substring(0,inputImageCode.length()-1);
//        System.out.println("改了一下后，用户输入变为：" + inputImageCode);
//        if (!captchaId.equals(inputImageCode)) {
//            System.out.println("输入错误");
//            String message ="账号密码错误";
//            return Result.fail(message);
//        } else {
//            System.out.println("输入正确");
//            return Result.success();
//        }
//    }
    @PostMapping("/verifyCode")
    public Object imgverifyControllerDefaultKaptcha(@RequestBody Review bean, HttpSession session,  HttpServletRequest request) {
        String inputImageCode = bean.getInputImageCode();
        String captchaId = (String) session.getAttribute("verifyCode");
//        System.out.println("验证码是：" + captchaId);
        System.out.println("用户输入的是：" + inputImageCode);
        if (!captchaId.equals(inputImageCode)) {
            System.out.println("输入错误");
            String message = "账号密码错误";
            return Result.fail(message);
        } else {
            System.out.println("输入正确");
            bean.setCreate_time(new Date());
            String ip = request.getRemoteAddr();
            //添加评论ip
            bean.setIp(ip);
            reviewService.add(bean);
            return Result.success();
        }
    }

}
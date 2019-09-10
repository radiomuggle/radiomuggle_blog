package com.muggle.blog.interceptor;

import com.muggle.blog.pojo.Pageview;
import com.muggle.blog.service.PageviewService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class ForeInterceptor implements HandlerInterceptor {
    @Autowired
    PageviewService pageviewService;

//    private Pageview pageview = new Pageview();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        Pageview pageview = new Pageview();
        HttpSession session = httpServletRequest.getSession();
        String contextPath=session.getServletContext().getContextPath();
        String[] requireAuthPages = new String[]{
                "home",
                "fore_article/",
//                "fore_category",
//                "article_all"

        };

        String uri = httpServletRequest.getRequestURI();

        uri = StringUtils.remove(uri, contextPath+"/");
        String page = uri;

        if(begingWith(page, requireAuthPages)){
//            httpServletResponse.sendRedirect("login");
            // 访问者的IP
            String ip = getRemortIP(httpServletRequest);
            // 访问地址
            String url = page;
            pageview.setIp(StringUtils.isEmpty(ip) ? "0.0.0.0" : ip);
            pageview.setUrl(StringUtils.isEmpty(url) ? "获取URL失败" : url);
            pageview.setCreate_time(new Date());
            pageviewService.add(pageview);
        }
        return true;
    }

    private boolean begingWith(String page, String[] requiredAuthPages) {
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages) {
            if(StringUtils.startsWith(page, requiredAuthPage)) {
                result = true;
                break;
            }
        }
        return result;
    }

public static String getRemortIP(HttpServletRequest request) {
                 String ip = request.getHeader("X-Forwarded-For");
                 if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
                         //多次反向代理后会有多个ip值，第一个ip才是真实ip
                         int index = ip.indexOf(",");
                         if(index != -1){
                                 return ip.substring(0,index);
                             }else{
                                 return ip;
                             }
                     }
                 ip = request.getHeader("X-Real-IP");
                 if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
                         return ip;
                     }
                 return request.getRemoteAddr();
             }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

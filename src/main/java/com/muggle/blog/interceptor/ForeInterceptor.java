package com.muggle.blog.interceptor;

import com.muggle.blog.pojo.Pageview;
import com.muggle.blog.service.PageviewService;
import com.muggle.blog.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
            String area_ip;
            pageview.setIp(StringUtils.isEmpty(ip) ? "0.0.0.0" : ip);
            pageview.setUrl(StringUtils.isEmpty(url) ? "获取URL失败" : url);
            pageview.setCreate_time(new Date());
//            if(StringUtils.contains(page, "admin_index")){
            if(StringUtils.contains(page, "home")){
                area_ip=getAreaIP(ip);
            }

            else
                area_ip="000";
            pageview.setArea_ip(area_ip);
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

    public static String getAreaIP(String remort_ip) {
        String host = "https://api01.aliyun.venuscn.com";
        String path = "/ip";
        String method = "GET";
        String appcode = "自己的appcode";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", remort_ip);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body

            String areaip = EntityUtils.toString(response.getEntity());
            System.out.println(areaip);
            return areaip;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

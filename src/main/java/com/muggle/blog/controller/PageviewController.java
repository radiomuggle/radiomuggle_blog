package com.muggle.blog.controller;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import com.muggle.blog.pojo.Pageview;
import com.muggle.blog.service.PageviewService;
import com.muggle.blog.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class PageviewController {
    @Autowired
    PageviewService pageviewService;

    @GetMapping("/pageviews")
    public Page4Navigator<Pageview> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Pageview> page =pageviewService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        for (Pageview p : page.getContent()) {
            String area_ip = p.getArea_ip();
            if(StringUtils.startsWith(area_ip, "{\"data\":{")) {
                JSONObject jsonObj = new JSONObject(area_ip);
                JSONObject jsonObj2 = new JSONObject(jsonObj.get("data").toString());
                p.setShow_ip(jsonObj2.get("country").toString()+" "+jsonObj2.get("region").toString()+jsonObj2.get("city").toString()+" "+jsonObj2.get("isp").toString());
            }
            else
                p.setShow_ip(" ");
        }
        return page;
    }

    @GetMapping("/pageviews/{id}")
    public Pageview get(@PathVariable("id") int id) throws Exception {
        Pageview bean=pageviewService.get(id);
        return bean;
    }

    @PostMapping("/pageviews")
    public Object add(@RequestBody Pageview bean) throws Exception {
        bean.setCreate_time(new Date());
        pageviewService.add(bean);
        return bean;
    }

    @DeleteMapping("/pageviews/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        pageviewService.delete(id);
        return null;
    }
}

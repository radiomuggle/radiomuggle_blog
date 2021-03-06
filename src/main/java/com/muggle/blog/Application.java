package com.muggle.blog;

import com.muggle.blog.util.PortUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableElasticsearchRepositories(basePackages = "com.muggle.blog.es")
@EnableJpaRepositories(basePackages = {"com.muggle.blog.dao", "com.muggle.blog.pojo"})
public class Application {
    static {
        PortUtil.checkPort(6379,"Redis 服务端",true);
        PortUtil.checkPort(9300,"ElasticSearch 服务端",true);
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setLocation("/data/apps/temp");
//        return factory.createMultipartConfig();
//    }
}
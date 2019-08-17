package com.wmeimob.fastboot.starter.common;

import com.wmeimob.fastboot.starter.common.entity.RichText;
import com.wmeimob.fastboot.starter.common.mapper.RichTextMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@SpringBootApplication
public class TestApplication {
    @Resource
    private RichTextMapper richTextMapper;
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class);
    }
    @RequestMapping("/")
    public RichText reply(){
        return richTextMapper.selectByPrimaryKey(9233);
    }

    @Bean
    ServletWebServerFactory servletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }
}

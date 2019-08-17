package com.wmeimob;

import com.wmeimob.fastboot.autoconfigure.security.RestSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 项目的启动类，通过SpringBootApplication注解下的
 * EnableAutoConfiguration 下的 AutoConfigurationPackage
 * 的注解对当前包和子包内进行配置 是项目初始化的地方
 * 详见 http://192.168.0.61:8888/duanhu/anmeila-
 *
 * @author loafer
 *
 */
@RestController
@SpringBootApplication
public class WmeimobFastbootApplication {
    //读取yml下的配置信息，配置rest客户端 内部类GLOBE有用户名和密码
    @Resource
    private RestSecurity restSecurity;

    public static void main(String[] args) {
        SpringApplication.run(WmeimobFastbootApplication.class, args);
    }

    /**
     *  在这里配置网络请求的客户端的拦截器
     *  对 Authorization 进行验证
     * @return restTemplate 网络请求客户端
     */
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate =new RestTemplate();
        //如果配置信息不为空在配置
        if (this.restSecurity != null && this.restSecurity.getGlobal() != null){
            restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(this.restSecurity.getGlobal().getUsername(), this.restSecurity.getGlobal().getPassword()));
        }
        return restTemplate;
    }


}

package com.wmeimob.fastboot.starter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // custom err 403
    @Resource
    private AccessDeniedHandler accessDeniedHandlerImpl;
    // custom err 401
    @Resource
    private AuthenticationEntryPoint authenticationEntryPointImpl;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private JwtAuthenticationProvider jwtAuthenticationProvider;


    // 这名字取得非常糟糕 ，实际上是jwt的配置信息


    private JsonWebToken jsonWebToken;

    protected static String[] PERMISSION_URLS;

    /**
     * 如果没配置的话的全部允许
     *
     * @param jsonWebToken 配置
     */
    @Autowired
    public WebSecurityConfig(JsonWebToken jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
        if (jsonWebToken != null && jsonWebToken.getPermissionUrls() != null && jsonWebToken.getPermissionUrls().length != 0) {
            PERMISSION_URLS = jsonWebToken.getPermissionUrls();
        } else {
            PERMISSION_URLS = new String[]{"/**"};
        }

    }

    protected void configure(AuthenticationManagerBuilder auth) {
        //自定义配置
        auth.authenticationProvider(this.jwtAuthenticationProvider);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // cool chain call !!!
        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                //把自定的过滤器添加到UsernamePasswordAuthenticationFilter之前
                // 很明显 这个“过滤器”只初始化了
                // jsonTokenHandler 和加解密方法
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //sessionManagement
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPointImpl)
                .accessDeniedHandler(this.accessDeniedHandlerImpl)
                .and()
                .authorizeRequests()
                // 添加不受拦截的url
                .antMatchers(PERMISSION_URLS).permitAll()
                // 任何尚未匹配的URL只需要验证用户即可访问
                .anyRequest().authenticated();
    }

}

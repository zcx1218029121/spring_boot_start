package com.wmeimob.fastboot.starter.security;

import com.wmeimob.fastboot.autoconfigure.security.RestSecurity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 在spring security 实际处理 请求是否通过的是 Provider
 * 在service 提供了接口
 * 在这里实际实现 authenticate
 *
 * @author loafer
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private RestSecurity httpBasicAuthProperties;

    private static final String HTTP_BASIC_AUTH_HEAD = "Basic ";
    private static final String SPLIT = ":";
    private static final List<GrantedAuthority> GRANTED_AUTHORITIES = new ArrayList<>();

    public JwtAuthenticationProvider() {
    }

    protected String encode(String username, String password) {
        String token = Base64Utils.encodeToString((username + SPLIT + password).getBytes(StandardCharsets.UTF_8));
        return HTTP_BASIC_AUTH_HEAD + token;
    }

    /**
     * 登录成功返回UserDetails，或者返回null如果登录失败
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticatedUser = null;
        // if authentication is  PreAuthenticatedAuthenticationToken or superclass PreAuthenticatedAuthenticationToken
        // 因为 JwtAuthenticationFilter extends RequestHeaderAuthenticationFilter
        // 自定义的 JwtAuthenticationFilter 组装并传过来一个PreAuthenticatedAuthenticationToken authRequest = new PreAuthenticatedAuthenticationToken(
        // principal, credentials);
        // 在这里判断的就是当前authentication是否是由 JwtAuthenticationFilter 传过来的
        // 如果是就进行自定义 判断 否则就不判断
        if (authentication.getClass().isAssignableFrom(PreAuthenticatedAuthenticationToken.class) && authentication.getPrincipal() != null) {
            String tokenHeader = (String) authentication.getPrincipal();
            if (tokenHeader != null && tokenHeader.startsWith(HTTP_BASIC_AUTH_HEAD)) {
                //如果请求头以 Basic 开头 对其进行 basic 64解码 秘钥错误就验证失败
                if (this.encode(this.httpBasicAuthProperties.getGlobal().getUsername(), this.httpBasicAuthProperties.getGlobal().getPassword()).equals(tokenHeader)) {
                    authenticatedUser = new JwtAuthentication(new User(this.httpBasicAuthProperties.getGlobal().getUsername(), this.httpBasicAuthProperties.getGlobal().getPassword(), true, true, true, true, GRANTED_AUTHORITIES));
                } else {
                    //如果请求头不是以 Basic 开头 就用 配置的json秘钥解析 秘钥错误就验证失败
                    UserDetails userDetails = this.jwtAuthenticationFilter.getJsonWebTokenHandler().decodeToken(tokenHeader);
                    if (userDetails != null) {
                        authenticatedUser = new JwtAuthentication(userDetails);
                    }
                }
            } else {
                authenticatedUser = authentication;
            }
            return authenticatedUser;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(PreAuthenticatedAuthenticationToken.class) || authentication.isAssignableFrom(JwtAuthentication.class);
    }

    static {
        GRANTED_AUTHORITIES.add(new SimpleGrantedAuthority("ACTUATOR"));
    }
}

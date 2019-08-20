package com.wmeimob.fastboot.starter.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * use to custom 401 Response
 * just  implements  AccessDeniedHandler
 * put json in to HttpServletResponse.
 *
 * @author loafer
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(401);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", httpServletResponse.getStatus());
        jsonObject.put("msg", "登录信息过期");
        httpServletResponse.getWriter().println(jsonObject);
        httpServletResponse.getWriter().flush();
    }
}

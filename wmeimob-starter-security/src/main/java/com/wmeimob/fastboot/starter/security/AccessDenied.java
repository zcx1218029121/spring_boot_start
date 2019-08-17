package com.wmeimob.fastboot.starter.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  loafer  modify version
 *  this class is implements both AccessDeniedHandler,AuthenticationEntryPoint
 *  is weird of in sound code that creat double class to implements AccessDeniedHandler
 *  and  AuthenticationEntryPoint
 * @author loafer
 */
@Deprecated
public class AccessDenied implements AccessDeniedHandler, AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        generateResponseJson(httpServletResponse, 401, null, e);
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        generateResponseJson(httpServletResponse, 403, "登录过期", e);
    }

    private void generateResponseJson(HttpServletResponse response, int state, @Nullable String msg, Exception e) throws IOException,ServletException  {
        response.setStatus(state);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", response.getStatus());

        if (msg == null) {
            jsonObject.put("msg", e.getLocalizedMessage());
        } else {
            jsonObject.put("msg", msg);
        }


        response.getWriter().println(jsonObject);
        response.getWriter().flush();


    }

}

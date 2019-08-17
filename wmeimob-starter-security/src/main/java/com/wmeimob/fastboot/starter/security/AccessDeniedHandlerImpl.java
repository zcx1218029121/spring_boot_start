package com.wmeimob.fastboot.starter.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * use to custom 403 Response
 * just  implements  AccessDeniedHandler
 * and put json in HttpServletResponse.
 * @author loafer
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(403);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", httpServletResponse.getStatus());
        jsonObject.put("msg", e.getLocalizedMessage());
        httpServletResponse.getWriter().println(jsonObject);
        httpServletResponse.getWriter().flush();
    }
}

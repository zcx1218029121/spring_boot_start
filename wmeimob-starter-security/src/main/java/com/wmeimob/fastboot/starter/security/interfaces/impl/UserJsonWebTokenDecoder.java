package com.wmeimob.fastboot.starter.security.interfaces.impl;



import com.wmeimob.fastboot.starter.security.interfaces.UserJsonWebTokenContract;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

public class UserJsonWebTokenDecoder implements UserJsonWebTokenContract.JsonWebTokenDecoder {
    private final static String CLAIMS_ID_KEY = "id";


    @Override
    public UserDetails decode(@NotNull Claims claims) {
        /*为什么要传入 .class 对象 ？
         因为JAVA 的泛型是伪泛型 运行的时候泛型是擦除的 要么强转，要么传个.class
         源码是 强转的
         int id=(Integer) claims.get(CLAIMS_ID_KEY); 没什么差别*/

        Integer id = (Integer)claims.get("id");
        User user = new User(String.valueOf(id), "", (Collection)null);
        return user;
    }
}

package com.wmeimob.fastboot.starter.security.context;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityContext<T extends UserDetails> {

    public SecurityContext() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends UserDetails> T getUser() {
        // 原来的代码在这里强转成强制UserDetails
        // 强制 向下转型 违反 里氏变换原则 并不建议！
        return (T) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

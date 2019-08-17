package com.wmeimob.fastboot.starter.security.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface JsonWebTokenReplayAttacksDefiniens {
    void define(UserDetails userDetails, String s);
}

package com.wmeimob.fastboot.starter.security.interfaces;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JsonWebTokenReplayAttacksDetector {
    void detect(String s, UserDetails userDetails) throws AuthenticationException;
}

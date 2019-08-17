package com.wmeimob.fastboot.starter.security.interfaces.impl;


import com.wmeimob.fastboot.starter.security.interfaces.UserJsonWebTokenContract;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class UserJsonWebTokenEncoder implements UserJsonWebTokenContract.JsonWebTokenEncoder {
    @Override
    public Map<String, Object> encode(@NotNull UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(UserJsonWebTokenContract.CLAIMS_ID_KEY, userDetails.getUsername());
        return claims;
    }
}

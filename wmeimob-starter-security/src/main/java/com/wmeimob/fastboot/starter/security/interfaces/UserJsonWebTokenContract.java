package com.wmeimob.fastboot.starter.security.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 改良了原有设计把两个接口聚合到合同类中
 * 一方面可以hold 多次使用的常量避免了hard code
 * 另一方面 加解密本来就是一个逻辑更易读
 * @author loafer
 */
public interface UserJsonWebTokenContract {

    String CLAIMS_ID_KEY = "id";


    interface JsonWebTokenDecoder {
        /**Decoder
         * @param claims claims
         * @return UserDetails
         */
        UserDetails decode(@NotNull Claims claims);
    }


    interface JsonWebTokenEncoder {
        /**Encoder
         * @param userDetails UserDetails
         * @return map claims
         */
        Map<String, Object> encode(@NotNull UserDetails userDetails);
    }

}

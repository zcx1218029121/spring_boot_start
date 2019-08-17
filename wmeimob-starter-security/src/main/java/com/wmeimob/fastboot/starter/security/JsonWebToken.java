package com.wmeimob.fastboot.starter.security;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *  read yml and make javabean of JsonWebToken Properties to config
 *  @author loafer
 */
@Configuration
@ConfigurationProperties(
        prefix = "jwt"
)
public class JsonWebToken {
    private Long expiration;
    private String header;
    private String secret;
    private String secretKey;
    private String[] permissionUrls;
    private String[] denyUrls;

    public Long getExpiration() {
        return expiration;
    }

    public void setDenyUrls(String[] denyUrls) {
        this.denyUrls = denyUrls;
    }

    public String getHeader() {
        return header;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getSecret() {
        return secret;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setPermissionUrls(String[] permissionUrls) {
        this.permissionUrls = permissionUrls;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String[] getDenyUrls() {
        return denyUrls;
    }

    public String[] getPermissionUrls() {
        return permissionUrls;
    }

}

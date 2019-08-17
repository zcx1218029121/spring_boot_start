package com.wmeimob.fastboot.starter.security;


import com.wmeimob.fastboot.starter.security.interfaces.JsonWebTokenReplayAttacksDefiniens;
import com.wmeimob.fastboot.starter.security.interfaces.JsonWebTokenReplayAttacksDetector;
import com.wmeimob.fastboot.starter.security.interfaces.UserJsonWebTokenContract;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


public class JsonWebTokenHandler implements Serializable {


    //=============================   filed  ========================================
    private static final String CLAIM_KEY_CREATED = "createdAt";

    private UserJsonWebTokenContract.JsonWebTokenDecoder jsonWebTokenDecoder;

    private UserJsonWebTokenContract.JsonWebTokenEncoder jsonWebTokenEncoder;

    private JsonWebTokenReplayAttacksDetector jsonWebTokenReplayAttacksDetector;

    private JsonWebTokenReplayAttacksDefiniens jsonWebTokenReplayAttacksDefiniens;

    private JsonWebToken jsonWebToken;

    //=============================   constructor  ========================================
    public JsonWebTokenHandler() {
    }
    //=============================   setting      ========================================


    public void setJsonWebToken(JsonWebToken jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    public void setJsonWebTokenDecoder(UserJsonWebTokenContract.JsonWebTokenDecoder jsonWebTokenDecoder) {
        this.jsonWebTokenDecoder = jsonWebTokenDecoder;
    }

    public void setJsonWebTokenEncoder(UserJsonWebTokenContract.JsonWebTokenEncoder jsonWebTokenEncoder) {
        this.jsonWebTokenEncoder = jsonWebTokenEncoder;
    }

    public void setJsonWebTokenReplayAttacksDefiniens(JsonWebTokenReplayAttacksDefiniens jsonWebTokenReplayAttacksDefiniens) {
        this.jsonWebTokenReplayAttacksDefiniens = jsonWebTokenReplayAttacksDefiniens;
    }

    public void setJsonWebTokenReplayAttacksDetector(JsonWebTokenReplayAttacksDetector jsonWebTokenReplayAttacksDetector) {
        this.jsonWebTokenReplayAttacksDetector = jsonWebTokenReplayAttacksDetector;
    }

    //=============================     method      ========================================

    /**
     * method of getUsername BY token
     *
     * @param token TOKEN
     * @return username(Nullable)
     */
    @Nullable
    public String getUsernameFromToken(String token) {
        String userName;
        try {
            Claims claims = getClaimsFromToken(token);
            assert claims != null;
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
            e.printStackTrace();
        }
        return userName;

    }

    /**
     * method of getClaimsFromToken
     *
     * @param token string token
     * @return claims(Nullable)
     */
    @Nullable
    private Claims getClaimsFromToken(String token) {
        String secret = this.jsonWebToken.getSecret();
        if (secret == null) {
            return null;
        } else {
            Claims claims;
            try {
                claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            } catch (Exception var5) {
                claims = null;
            }
            return claims;
        }
    }

    /**
     * method of get CreatedDateFromToken
     *
     * @param token string token
     * @return the data From Token
     */
    @Nullable
    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            Claims claims = getClaimsFromToken(token);
            assert claims != null;
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * method of get expiration date from token
     *
     * @param token string token
     * @return data of expiration date from token
     */
    @Nullable
    private Date getExpirationDateFromToken(String token) {
        Date expirationDate;
        try {
            Claims claims = getClaimsFromToken(token);
            expirationDate = claims.getExpiration();
        } catch (Exception e) {
            expirationDate = null;
        }
        return expirationDate;
    }

    /**
     * method of generateExpirationDate
     *
     * @return date
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.jsonWebToken.getExpiration() * 1000L);
    }

    /**
     * check the token is Expired
     *
     * @param token string token
     * @return TokenExpired
     */
    private Boolean isTokenExpired(String token) {
        Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = jsonWebTokenEncoder.encode(userDetails);
        String token = generateToken(claims);
        //call back of Attacks deficient
        this.jsonWebTokenReplayAttacksDefiniens.define(userDetails, token);

        return token;
    }

    /**
     * generateToken sign in here by hs512
     *
     * @param claims
     * @return
     */
    @Nullable
    private String generateToken(Map<String, Object> claims) {
        String secret = this.jsonWebToken.getSecret();
        if (secret == null) {
            return null;
        } else {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(this.generateExpirationDate())
                    // sign in here by HS512
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        }
    }

    /**
     * @param token             string token
     * @param lastPasswordReset data of  lastPasswordReset
     */
    // TODO: 2019/8/17  如果有重置密码就刷新token
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        this.getCreatedDateFromToken(token);
        // unused param  lastPasswordReset !!
        return !this.isTokenExpired(token);
    }

    /**
     * 刷新 token
     *
     * @param token string token
     * @return new token
     */

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = this.getClaimsFromToken(token);
            assert claims != null;
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = this.generateToken(claims);
        } catch (Exception var4) {
            refreshedToken = null;
        }

        return refreshedToken;
    }

    public UserDetails validateToken(String token, UserDetails userDetails) {
        String username = this.getUsernameFromToken(token);
        boolean result = username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
        return result ? userDetails : null;
    }

    public UserDetails decodeToken(String token) {
        Claims claims = this.getClaimsFromToken(token);
        if (claims != null && !this.isTokenExpired(token)) {
            UserDetails userDetails = this.jsonWebTokenDecoder.decode(claims);
            if (userDetails == null) {
                return null;
            } else {
                this.jsonWebTokenReplayAttacksDetector.detect(token, userDetails);
                return userDetails;
            }
        } else {
            return null;
        }
    }


}

package com.wmeimob.fastboot.starter.security;

import com.wmeimob.fastboot.starter.security.interfaces.impl.UserJsonWebTokenDecoder;
import com.wmeimob.fastboot.starter.security.interfaces.impl.UserJsonWebTokenEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * custom AuthenticationFilter that with @{JsonWebTokenHandler}
 * init config head and  JsonWebTokenHandler
 *
 * @author loafer
 */
@Component
public final class JwtAuthenticationFilter extends RequestHeaderAuthenticationFilter {
    private JsonWebTokenHandler jsonWebTokenHandler;
    public static final String DEFAULT_HEADER = "Authorization";
    private AuthenticationManager authenticationManager;

    public void setJsonWebTokenHandler(JsonWebTokenHandler jsonWebTokenHandler) {
        this.jsonWebTokenHandler = jsonWebTokenHandler;
    }

    public JsonWebTokenHandler getJsonWebTokenHandler() {
        return this.jsonWebTokenHandler;
    }

    @Autowired
    public JwtAuthenticationFilter(JsonWebToken jsonWebToken) {
        JsonWebTokenHandler jwtHandler = new JsonWebTokenHandler();
        this.setExceptionIfHeaderMissing(false);
        String queryHeader = DEFAULT_HEADER;
        if (jsonWebToken != null && jsonWebToken.getHeader() != null) {
            queryHeader = jsonWebToken.getHeader();
        }

        this.setPrincipalRequestHeader(queryHeader);
        jwtHandler.setJsonWebToken(jsonWebToken);
        jwtHandler.setJsonWebTokenDecoder(new UserJsonWebTokenDecoder());
        jwtHandler.setJsonWebTokenEncoder(new UserJsonWebTokenEncoder());
        jwtHandler.setJsonWebTokenReplayAttacksDetector((a, b) -> {
        });
        jwtHandler.setJsonWebTokenReplayAttacksDefiniens((a, b) -> {
        });
        this.jsonWebTokenHandler = jwtHandler;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setAuthenticationManager(authenticationManager);
    }

    public AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }
}

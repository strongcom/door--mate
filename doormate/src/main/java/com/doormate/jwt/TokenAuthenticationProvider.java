package com.doormate.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        LoginAuthToken loginAuthToken = (LoginAuthToken) authentication;

        loginAuthToken.setAuthenticated(true);
        return loginAuthToken;
    }



    @Override
    public boolean supports(Class<?> authentication) {
        // 인증객체에서 선언한 객체는 reflection 을 통해서 해당 객체인지 파악하는데 이용된다.
        return LoginAuthToken.class.isAssignableFrom(authentication);
    }
}

package com.doormate.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class LoginAuthToken extends AbstractAuthenticationToken {

    private final String principal;
    private Object credentials;

    public LoginAuthToken(String principal){
        super(null);
        this.principal = principal;
        this.credentials = null;
    }


    @Override
    public Object getCredentials() {
        return credentials;
    }


    @Override
    public Object getPrincipal() {
        return principal;
    }
}

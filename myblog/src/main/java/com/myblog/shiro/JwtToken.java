package com.myblog.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.stereotype.Component;


public class JwtToken implements AuthenticationToken {
    private String token;
    public JwtToken(String jwt){
        this.token = jwt;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

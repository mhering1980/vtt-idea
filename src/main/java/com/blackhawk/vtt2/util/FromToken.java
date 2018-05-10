package com.blackhawk.vtt2.util;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class FromToken {

    public Integer getVolunteerId(Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)principal;
        Claims claims = (Claims) token.getPrincipal();
        return claims.get("volunteerId", Integer.class);
    }

    public Boolean getAdminStatus(Principal principal){
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)principal;
        Claims claims = (Claims) token.getPrincipal();
        return claims.get("admin", Boolean.class);
    }
}

package com.blackhawk.vtt2.security;

import com.blackhawk.vtt2.dao.VolunteerRepository;
import com.blackhawk.vtt2.model.VolunteerCredential;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.blackhawk.vtt2.security.SecurityConstants.EXPIRATION_TIME;
import static com.blackhawk.vtt2.security.SecurityConstants.HEADER_STRING;
import static com.blackhawk.vtt2.security.SecurityConstants.TOKEN_PREFIX;
import static com.blackhawk.vtt2.security.SecurityConstants.SECRET;
import com.blackhawk.vtt2.dao.VolunteerCredentialRepository;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    @Autowired
    private VolunteerCredentialRepository volunteerCredentialRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;

    private Integer id = null;
    private Boolean admin = false;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
           VolunteerCredential cred =  new ObjectMapper()
                    .readValue(request.getInputStream(), VolunteerCredential.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            cred.getUsername(),
                            cred.getPassword(),
                            new ArrayList<>())
                    );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {

        VttUser vttUser = (VttUser)auth.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("volunteerId", vttUser.getVolunteer().getId());
        claims.put("admin", vttUser.getVolunteer().getIsAdmin());

        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}

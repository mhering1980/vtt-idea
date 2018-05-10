package com.blackhawk.vtt2.security;

import javax.print.DocFlavor;

public class SecurityConstants {

    public static final String SECRET = "secret";
    public static final long EXPIRATION_TIME = 86_400_000; // 1day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

}

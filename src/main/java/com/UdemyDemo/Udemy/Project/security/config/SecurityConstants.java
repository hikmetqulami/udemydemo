package com.UdemyDemo.Udemy.Project.security.config;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/auth/**";
    public static final String SECRET = "ceqXTi2Mb0R53U4ZW5VczPCiBY70Zy4WzSQXk8OZ3P43yKshz2";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
//    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 60000; //10min

}

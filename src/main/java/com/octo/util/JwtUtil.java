package com.octo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author zms
 */
public class JwtUtil {
    private static final String SECRET_KEY = "zms";
    private static final long EXPIRATION_TIME = 30 * 60 * 10000;

    /**
     * 生成token
     *
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + JwtUtil.EXPIRATION_TIME);
        String token = JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(JwtUtil.SECRET_KEY));
        return token;
    }

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public static boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(JwtUtil.SECRET_KEY)).build().verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getUsername(String token) {
        DecodedJWT decode = JWT.decode(token);
        return decode.getSubject();
    }

    public static String getUsername(HttpServletRequest request) {
        String username = "";
        // 获取 Authorization 头部的值
        String authorizationHeader = request.getHeader("Authorization");
        // 解析 Bearer Token
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // 从 "Bearer " 后面开始截取
            username = JwtUtil.getUsername(token);
        }
        return username;
    }
}

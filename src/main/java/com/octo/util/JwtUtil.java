package com.octo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

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
}

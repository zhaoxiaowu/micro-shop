package com.zxw.sso.sdk;

import com.zxw.common.web.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、标识）：
 * {"sub":"wu","created":1489079981393,"jid":uuid}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 * @description: TokenHelper工具类
 */
@Slf4j
@Component
public class TokenHelper {

    @Value("${sys_jwt_secret:mySecret}")
    private String secret;

    /**
     * 根据负责生成JWT的token
     */
    public String generateToken(Map<String, Object> claims) throws Exception {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();


    }

    /**
     * 从token中获取JWT中的负载
     */
    public Claims getClaimsFromToken(String token) throws BusinessException {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
            throw new BusinessException(SsoErrorCode.UNAUTHORIZED);
        }
        return claims;
    }

}
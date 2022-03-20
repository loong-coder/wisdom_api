package com.edu.util;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.edu.cache.CacheManager;
import com.edu.constant.CacheConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
public class JwtUtils {

    @Autowired
    private CacheManager<String> cacheManager;

    @Value("${wisdom.jwt.secret}")
    private String secret;

    @Value("${wisdom.jwt.expire}")
    private Integer access_token_expiration;

    @Value("${wisdom.jwt.expire}")
    private Long refresh_token_expiration;


    public String generateJwt(String userId, String email){
        //设置token时间 过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, access_token_expiration);
        Date date = nowTime.getTime();

        String token = JWT.create()
                .withClaim(CacheConstant.CLAIM_EMAIL, email)
                .withClaim(CacheConstant.CLAIM_USER_ID, userId)
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secret));
        final String key = wrapperKey(email);
        cacheManager.put(key, token, Long.valueOf(access_token_expiration), TimeUnit.SECONDS);
        return token;
    }

    private final String wrapperKey(String email) {
        String key = CacheConstant.TOKEN_CACHE + email;
        return key;
    }

    public void logout(){
        String key = wrapperKey(UserContextUtils.getUserEmail());
        cacheManager.delete(key);
    }
    /**
     * 验证token的有效性
     * */
    public boolean verify(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            verifier.verify(token);
            String email = getClaim(token, CacheConstant.CLAIM_EMAIL);
            return StrUtil.equals(cacheManager.get(wrapperKey(email)), token);
        }catch (Exception e){
            return false;
        }
    }


    public String getClaim(String token, String name){
        String claim = null;
        try {
            claim =  JWT.decode(token).getClaim(name).asString();
        }catch (Exception e) {
            return "getClaimFalse";
        }
        return claim;
    }
}

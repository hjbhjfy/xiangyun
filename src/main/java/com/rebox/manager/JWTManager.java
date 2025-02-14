package com.rebox.manager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rebox.domain.dto.UserDTO;
import com.rebox.util.JSONUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWTVerifier;

/**
 * 通用的业务功能，放在manager包下
 *
 * 此类就是一个jwt的通用功能
 *
 */
@Component
public class JWTManager {

    /**
     * 密钥，最好写得灵活一些，不要写死
     */
    @Value("${rebox.jwt.secret}")
    private String secret;

    /**
     * 生成JWT
     */
    public String createToken(String userJSON) {
        //组装头数据
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        return JWT.create() //创建jwt的字符串
                //1、头：
                .withHeader(header)

                //2、负载：可以给多个负载数据，目前我们是给一个负载数据
                .withClaim("user", userJSON)

                //3、签名：签名算法
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 验证JWT是否被篡改
     *
     * @param token 要验证的jwt的字符串
     */
    public Boolean verifyToken(String token) {
        try {
            // 使用秘钥创建一个解析对象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();

            //验证JWT，该方法如果没有报异常就表示token验证通过，如果报了异常就表示token验证不通过
            jwtVerifier.verify(token);

            return true;
        } catch (Exception e) {
            //发生了异常说明token没有验证通过
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从token中获取登录人的id
     *
     * @param token
     * @return
     */
    public Integer getLoginUserId(String token) {
        // 使用秘钥创建一个解析对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();

        //验证JWT，该方法如果没有报异常就表示token验证通过，如果报了异常就表示token验证不通过
        DecodedJWT decodedJWT = jwtVerifier.verify(token); //得到解码后的jwt

        //根据负载字段名字得到负载数据
        Claim claim = decodedJWT.getClaim("user");

        String userJSON = claim.asString(); // {"id": 12, "name" : "admin“, "token" : null}

        //把负载数据的json字符串转成java对象
        UserDTO userDTO = JSONUtils.readValue(userJSON, UserDTO.class);

        return userDTO.getId();
    }
}

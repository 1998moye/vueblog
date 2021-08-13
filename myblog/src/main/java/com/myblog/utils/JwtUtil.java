package com.myblog.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;

public class JwtUtil {
    static byte[] secret = new byte[32];
    public static String makeToken(long userId){
        SecureRandom random = new SecureRandom();

        random.nextBytes(secret);
        String token = null;
        try {
            MACSigner signer = new MACSigner(secret);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(userId + "")
                    .issueTime(new Date())
                    .expirationTime(new Date(new Date().getTime() + 60000 * 60 * 12))
                    .build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (KeyLengthException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("生成token错误");
        } catch (JOSEException e) {
            throw new IllegalArgumentException("token序列异常");
        }
        return token;
    }


    public static JWTClaimsSet parseToken(String token){
        SignedJWT parse = null;
        JWTClaimsSet jwtClaimsSet = null;
        try {
            parse = SignedJWT.parse(token);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            jwtClaimsSet = parse.getJWTClaimsSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jwtClaimsSet;
    }

    public static boolean isExpired(Date expire){
        return expire.before(new Date());
    }

}

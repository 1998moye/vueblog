package com.test;

import com.myblog.service.IMUserService;
import com.myblog.shiro.JwtToken;
import com.myblog.shiro.ShiroRealm;
import com.myblog.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShiroRelamTest {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ShiroRealm shiroRealm;

    @Autowired
    IMUserService userService;


//    @Test
//    public void realmTest(){
//        String token = jwtUtil.makeToken(1L);
//        shiroRealm.doGetAuthenticationInf(new JwtToken(token));
//    }
}

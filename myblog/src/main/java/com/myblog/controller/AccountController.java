package com.myblog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myblog.entity.LoginDto;
import com.myblog.entity.MUser;
import com.myblog.res.Result;
import com.myblog.service.IMUserService;
import com.myblog.utils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {
    @Autowired
    IMUserService userService;


    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        Assert.notNull(loginDto, "信息不能为空");
        String username = loginDto.getUsername();
        MUser user = userService.getOne(new QueryWrapper<MUser>().eq("username", username));
        Assert.notNull(user, "用户不存在");
//        System.out.println(loginDto.getPassword());
//        System.out.println(user.getPassword());
//        System.out.println(SecureUtil.md5(loginDto.getPassword()));
//        System.out.println();
        Assert.isTrue(user.getPassword().equals(SecureUtil.md5(loginDto.getPassword())), "密码错误");
        Long userId = user.getId();
        String token = JwtUtil.makeToken(userId);
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.success(null, MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                        .put("avatar", user.getAvatar())
                        .put("email", user.getEmail()).map());
    }

    @GetMapping("/logout")
    public Result logout(){
        System.out.println(SecurityUtils.getSubject().isAuthenticated());
        if(SecurityUtils.getSubject().isAuthenticated()){
            SecurityUtils.getSubject().logout();
        }
        return Result.success("退出成功", null);
    }

    @GetMapping("/isdenglu")
    public void isdenglu(){
        System.out.println(SecurityUtils.getSubject().isAuthenticated());
    }
}

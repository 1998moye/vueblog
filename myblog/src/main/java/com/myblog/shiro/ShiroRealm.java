package com.myblog.shiro;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.myblog.entity.AccountProfile;
import com.myblog.entity.MUser;
import com.myblog.service.IMUserService;
import com.myblog.utils.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiroRealm extends AuthorizingRealm {


    @Autowired
    IMUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken)authenticationToken;
        System.out.println(jwtToken);
        String userId = JwtUtil.parseToken((String) jwtToken.getPrincipal()).getSubject();
        MUser user = userService.getById(userId);
        if(user == null){
            throw new UnknownAccountException("用户不存在");
        }else if(user.getStatus() == -1){
            throw new LockedAccountException("用户被锁定");
        }
        AccountProfile accountProfile = new AccountProfile();
        BeanUtil.copyProperties(user, accountProfile, new CopyOptions().ignoreNullValue());
        System.out.println(accountProfile.getId());
        return new SimpleAuthenticationInfo(accountProfile, jwtToken.getCredentials(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}

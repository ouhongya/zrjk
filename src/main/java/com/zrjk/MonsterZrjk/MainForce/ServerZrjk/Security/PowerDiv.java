package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;

/**
 * 自定义 权限校验
 */
@Component
public class PowerDiv implements PermissionEvaluator {

    @Autowired
    private HttpServletRequest request;


    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
            //获取登陆时 security的user对象
        User user = (User) authentication.getPrincipal();
        //获取登陆时授的权限
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String powerUrl = authority.getAuthority();

            //获取当前url 与权限url对比
            String nowUrl = request.getRequestURI();

            if(powerUrl.equals(nowUrl)){
                return true;
            }
        }
                return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}

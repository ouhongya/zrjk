package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.Security.Impl;


    import com.zrjk.MonsterZrjk.Common.Utils.FileUpLoad;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.Account;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Mapper.AccountMapper;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.Security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    @Autowired
    private AccountMapper accountMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查询密码
        Account account = accountMapper.findAccountByUsername(username);
        if(account!=null){
            //查询账户拥有的按钮
            List<String> buttonUrls= accountMapper.findButtonUrlById(account.getId());
            //对账户授权
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (String buttonUrl : buttonUrls) {
                grantedAuthorities.add(new SimpleGrantedAuthority(buttonUrl));
            }

            User user = new User(username,account.getPassword(),grantedAuthorities);

            return user;
        }else {
            return null;
        }
    }
}

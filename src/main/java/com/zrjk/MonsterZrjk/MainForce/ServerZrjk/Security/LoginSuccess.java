package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.Security;


import com.zrjk.MonsterZrjk.Common.Utils.TokenUtil;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.Account;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功
 */
public class LoginSuccess extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private AccountMapper accountMapper;

    @Value("${tokenTime}")
    private Long tokenTime;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
            //构建token需要存入的数据
        User user = (User) authentication.getPrincipal();

        Account account = accountMapper.findAccountByUsername(user.getUsername());

        Map<String,Object> tokenMap = new HashMap<>();

        tokenMap.put("uid",account.getId());  //账户id

        tokenMap.put("endTime",new Date().getTime()+tokenTime);

        String token = tokenUtil.createToken(tokenMap);

        //将token设置到响应头中
          response.setHeader("token",token);

          response.getWriter().print("111");

    }
}

package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.Security;

import com.zrjk.MonsterZrjk.Common.Utils.TokenUtil;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.Account;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckTokenFilter extends BasicAuthenticationFilter {

    @Value("${tokenTime}")
    private long tokenTime;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private AccountMapper accountMapper;

    public CheckTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            //从cookie中获取token
        String token = null;

        Cookie[] cookies = request.getCookies();

        if(cookies==null||cookies.length<=0){
            chain.doFilter(request,response);
        }else {
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    token=cookie.getValue();
                }
            }

            if(token!=null){
                Map<String, Object> tokenMap = tokenUtil.checkToken(token);

                String state = (String) tokenMap.get("state");

                if("还没过期".equals(state)){
                    //生成新的token
                    Map<String,Object> dataMap = (HashMap<String, Object>) tokenMap.get("data");

                    dataMap.put("endTime",new Date().getTime()+tokenTime);

                    String token1 = tokenUtil.createToken(dataMap);

                    //设置到响应头
                    response.setHeader("token",token1);

                    //查询用户名
                    Account account=accountMapper.findAccountById((String)dataMap.get("uid"));
                    //为本次会话设置权限
                    User user = (User) userDetailsService.loadUserByUsername(account.getUsername());

                    //创建token通过认证
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 将权限写入本次会话
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    //放行
                    chain.doFilter(request,response);
                }else {
                    chain.doFilter(request,response);
                }
            }else {
                chain.doFilter(request,response);
            }
        }
    }
}

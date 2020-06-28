package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public LoginSuccess myLoginSuccess(){
        return new LoginSuccess();
    }

    @Bean
    public LoginFail myLoginFail(){
        return new LoginFail();
    }

    @Bean
    public NoPower myNoPower(){
        return new NoPower();
    }

    @Bean
    public BCryptPasswordEncoder myEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PowerDiv powerDiv;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    //启动自定义权限控制器
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(powerDiv);
        return handler;
    }

    //校验token过滤器
    @Bean
    public CheckTokenFilter myCheckTokenFilter(){
        CheckTokenFilter checkTokenFilter = null;
        try {
            checkTokenFilter=new CheckTokenFilter(super.authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return checkTokenFilter;
        }
    }

    /**
     * 拦截规则 和 各种过滤器配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //拦截规则
            http.authorizeRequests().antMatchers("/server/**").authenticated()
                    //登录页面  登录请求url   登录成功  登录失败处理类
                    .and().formLogin().loginProcessingUrl("/server/login")./*loginPage("/login").*/successHandler(myLoginSuccess()).failureHandler(myLoginFail())
                    //登出
                    .and().logout().logoutUrl("/server/logout")
                    //token校验过滤器
                    .and().addFilter(myCheckTokenFilter())
                    //权限不足
                    .exceptionHandling().accessDeniedHandler(myNoPower())
                    //关闭跨域请求伪造
                    .and().csrf().disable();
                    // 禁用session
                    //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 配置登录的userDetailsService和解码类
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(myEncoder());
    }
}

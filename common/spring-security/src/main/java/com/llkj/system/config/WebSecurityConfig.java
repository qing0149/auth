package com.llkj.system.config;

import com.llkj.system.filter.TokenAuthenticationFilter;
import com.llkj.system.filter.TokenLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName WebSecurityConfig
 * @Description TODO
 * @Author qing
 * @Date 2022/12/27 21:40
 * @Version 1.0
 */
@SpringBootConfiguration
@EnableWebSecurity//启动SpringSecurity的默认行文
@EnableGlobalMethodSecurity(prePostEnabled=true)//SpringSecurity默认不支持方法的注解,通过这个注解开启
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 配置哪些请求不拦截
     * 排除swagger相关请求
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
    }
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //添加自定义过滤器，取消CSRF保护（页面上没有隐藏的csrf字段，使用JWT又可以避免这个攻击）
        http.csrf().disable();
        //开启跨域
        http.cors();
        //资源访问机制
        http.authorizeRequests()
        //       .antMatchers("/admin/system/index/login","/admin/system/index/info").permitAll()//直接放行
                .anyRequest().authenticated();//必须认证通过
        //添加自定义过滤器
       //        authenticationManager();WebSecurityConfigurerAdaptert中提供的方法
        http.addFilter(new TokenLoginFilter(authenticationManager(),redisTemplate));
        http.addFilterBefore(new TokenAuthenticationFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class);
        //禁用session(JWT是从客户都安传递到服务器中的)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

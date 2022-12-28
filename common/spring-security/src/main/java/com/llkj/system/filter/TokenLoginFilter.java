package com.llkj.system.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llkj.common.helper.JwtHelper;
import com.llkj.common.result.Result;
import com.llkj.common.util.ResponseUtil;
import com.llkj.model.system.SysUser;
import com.llkj.model.vo.LoginVo;
import com.llkj.system.customer.CustomerUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private RedisTemplate redisTemplate;
    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate) {
        this.redisTemplate=redisTemplate;
        this.setAuthenticationManager(authenticationManager);
//        this.authenticationManager=authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
//        request.getParameter("loginVo");
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
//        获取用户输入的用户名和密码
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());

//        封装到Authentication中

//        调用AuthenticationManager进行认证
            return this.getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //成功之后调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomerUser customerUser = (CustomerUser) authResult.getPrincipal();
        //用户的权限怎么办放在redis中
        Collection<GrantedAuthority> authorities = customerUser.getAuthorities();
        //操作字符串类型
        redisTemplate.boundValueOps(customerUser.getSysUser().getUsername()).set(authorities);



        //返回令牌
        Map<String, Object> map = new HashMap<>();

        map.put("token", JwtHelper.createToken(customerUser.getSysUser().getId(), customerUser.getSysUser().getUsername()));

        ResponseUtil.out(response, Result.ok(map));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response,Result.build(null,444,failed.getMessage()));
    }
}

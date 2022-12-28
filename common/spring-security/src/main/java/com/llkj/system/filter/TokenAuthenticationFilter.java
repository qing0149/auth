package com.llkj.system.filter;


import com.llkj.common.helper.JwtHelper;
import com.llkj.common.result.Result;
import com.llkj.common.result.ResultCodeEnum;
import com.llkj.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * @ClassName TokenAythenticationFilter
 * @Description TODO
 * @Author qing
 * @Date 2022/12/28 1:16
 * @Version 1.0
 */
/*
令牌验证的过滤器
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //如果是登录接口，/admin/system/index/login直接放行
        if ("/admin/system/index/login".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        //拿到令牌，并且根据用户名得到到Authentication
        Authentication authentication = getAuthentication(request);
        //如果Authentication部位空放行，返回异常提醒
        if (authentication != null) {
            //认证通过
//            从上下文中(后面授权时，判断是否又指定授权时，底层过滤器从上下文中获取authentcation)
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
//放行
            filterChain.doFilter(request, response);

        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        //获取令牌(可能是伪造的)
        String token = request.getHeader("token");
        logger.info("token" + token);
        Authentication authentication = null;
        //如果能从令牌中获取用户名（能拿到用户名，就不是伪造的，相当于通过了验证）
        if (!StringUtils.isEmpty(token)) {
            String username = JwtHelper.getUsername(token);
            logger.info("username" + username);
            //todo:还没有用户的权限
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)redisTemplate.boundValueOps(JwtHelper.getUserId(token)).get();
            authentication = new UsernamePasswordAuthenticationToken(username, authorities, Collections.emptyList());
        }

        //封装到Authentication中，有可能是null
        return authentication;
    }
}

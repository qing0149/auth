package com.llkj.system.security;

import com.llkj.common.result.ResultCodeEnum;
import com.llkj.model.system.SysUser;
import com.llkj.system.customer.CustomerUser;
import com.llkj.system.exception.LlkjException;
import com.llkj.system.service.SysUserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName UserDetailServiceImpl
 * @Description TODO
 * @Author qing
 * @Date 2022/12/27 14:10
 * @Version 1.0
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //访问数据库根据用户名获取用户信息
        SysUser sysUser = this.sysUserService.getByUserName(username);
        //判断用户名是否正确
        if(sysUser==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //判断账户是否禁用
        if(sysUser.getStatus()==0){
            throw new LlkjException(ResultCodeEnum.ACCOUNT_STOP);
        }
        List<String> btnPermList = this.sysUserService.getUserButtonPermissionsByUserId(sysUser.getId());
        //就是不比较密码（调用这个方法后SpringSecurity来通过PasswordEncoder来比较）

        //返回用户数据
        //目前认证阶段暂时不提供权限列表，后面授权时提供，需要查询数据库！！！！！ 查询出用户的权限
//        List<String> btnPermList =  this.sysUserService.getUserButtonPermissionsByUserId(sysUser.getId());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String btnPerm :btnPermList){
            authorities.add(new SimpleGrantedAuthority(btnPerm));
        }
//        authorities.add(new SimpleGrantedAuthority("bnt.sysUser.remove"));
//        authorities.add(new SimpleGrantedAuthority("bnt.sysRole.list"));
        CustomerUser customerUser = new CustomerUser(sysUser,authorities);
        return customerUser;
    }
}

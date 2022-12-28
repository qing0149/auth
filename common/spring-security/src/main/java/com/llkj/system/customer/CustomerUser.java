package com.llkj.system.customer;

import com.llkj.model.system.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @ClassName CustomerUser
 * @Description TODO
 * @Author qing
 * @Date 2022/12/27 14:06
 * @Version 1.0
 */
public class CustomerUser extends User {
    private SysUser sysUser;

    /**
     *
     * @param sysUser 从数据库中查询出来的用户信息
     * @param authorities 从数据库中查询出来的权限
     */
    public CustomerUser(SysUser sysUser,Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}

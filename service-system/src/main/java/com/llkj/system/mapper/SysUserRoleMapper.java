package com.llkj.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llkj.model.system.SysUserRole;

/**
 * @ClassName SysUserRoleController
 * @Description TODO
 * @Author qing
 * @Date 2022/12/23 8:58
 * @Version 1.0
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    SysUserRole selectByUserId(Long id);
}

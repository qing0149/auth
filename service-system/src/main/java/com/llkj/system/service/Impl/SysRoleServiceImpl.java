package com.llkj.system.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llkj.model.system.SysRole;
import com.llkj.model.vo.SysRoleQueryVo;
import com.llkj.system.mapper.SysRoleMapper;
import com.llkj.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysRoleServiceImpl
 * @Description TODO
 * @Author qing
 * @Date 2022/12/16 11:40
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo) {
        IPage<SysRole> sysRoleIPage = sysRoleMapper.selectPage(pageParam, roleQueryVo);
        return sysRoleIPage;
    }
}

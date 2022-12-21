package com.llkj.system.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llkj.model.system.SysUser;
import com.llkj.model.vo.AssginRoleVo;
import com.llkj.model.vo.SysUserQueryVo;
import com.llkj.system.mapper.SysUserMapper;
import com.llkj.system.service.SysRoleService;
import com.llkj.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName SysUserServiceImpl
 * @Description TODO
 * @Author qing
 * @Date 2022/12/20 23:19
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo userQueryVo){

        return sysUserMapper.selectPage(pageParam, userQueryVo);
    }

    @Override
    public void updateStatues(Long id, Integer statues) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(statues);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {

        return null;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {

    }
}

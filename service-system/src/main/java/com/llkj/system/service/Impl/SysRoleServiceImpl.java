package com.llkj.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llkj.model.system.SysRole;
import com.llkj.model.system.SysUser;
import com.llkj.model.system.SysUserRole;
import com.llkj.model.vo.AssginRoleVo;
import com.llkj.model.vo.SysRoleQueryVo;
import com.llkj.system.mapper.SysRoleMapper;
import com.llkj.system.mapper.SysUserRoleMapper;
import com.llkj.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysRoleServiceImpl
 * @Description TODO
 * @Author qing
 * @Date 2022/12/16 11:40
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo) {
        IPage<SysRole> sysRoleIPage = sysRoleMapper.selectPage(pageParam, roleQueryVo);
        return sysRoleIPage;
    }

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {

        //获取所有的角色
        List<SysRole> roleList = sysRoleMapper.selectList(null);
//        根据用户id查询
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.select("role_id");
        //获取用户已经分配的角色
        List<SysUserRole> userRoleList = sysUserRoleMapper.selectList(wrapper);
//        获取用户已分配的角色id
        ArrayList<Long> userRoleIdList = new ArrayList<>();
        userRoleList.stream().forEach(a -> userRoleIdList.add(a.getRoleId()));
//        返回创建的map
        Map map = new HashMap<>();
        map.put("allRoles", roleList);
        map.put("userRoleIds", userRoleIdList);
        return map;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除原来分配的角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", assginRoleVo.getUserId());
        sysUserRoleMapper.delete(wrapper);
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        roleIdList.forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(assginRoleVo.getUserId());
            sysUserRoleMapper.insert(sysUserRole);
        });
    }
}

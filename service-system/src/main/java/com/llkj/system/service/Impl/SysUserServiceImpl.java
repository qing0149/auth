package com.llkj.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llkj.common.helper.MenuHelper;
import com.llkj.common.helper.RouterHelper;
import com.llkj.model.system.SysMenu;
import com.llkj.model.system.SysRole;
import com.llkj.model.system.SysUser;
import com.llkj.model.system.SysUserRole;
import com.llkj.model.vo.AssginRoleVo;
import com.llkj.model.vo.RouterVo;
import com.llkj.model.vo.SysUserQueryVo;
import com.llkj.system.mapper.SysMenuMapper;
import com.llkj.system.mapper.SysUserMapper;
import com.llkj.system.mapper.SysUserRoleMapper;
import com.llkj.system.service.SysRoleService;
import com.llkj.system.service.SysUserService;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysUserServiceImpl
 * @Description TODO
 * @Author qing
 * @Date 2022/12/20 23:19
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo userQueryVo) {

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
    public SysUser getByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        return sysUser;
    }

    @Override
    public Map getUserInfoByUserId(Long userId) {
//1.获取当前用户菜单信息
        SysUser sysUser = sysUserMapper.selectById(userId);
//        2.获取当前用户的菜单信息(多表查询)
        List<SysMenu> menuList = getUserMenusByUserId(userId);
//        3.
        List<SysMenu> menuTreeList = MenuHelper.buildTree(menuList);
//        将数值钻杆的列表转换成Json字符串，使用FastJson自动转换成Json

        List<RouterVo> routerVoList = RouterHelper.buildRouters(menuTreeList);
//获取菜单中的
        List<String> buttonPermissions = getUserButtonPermissionsByUserId(userId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", sysUser.getUsername());
        map.put("avatar", "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
//        菜单权限
        map.put("buttons", buttonPermissions);
//        map.put("buttons", );
//        按钮权限
        map.put("routers", routerVoList);
        return map;
    }

    public List<String> getUserButtonPermissionsByUserId(Long userId) {
        //调用根据用户id获取权限菜单的方法
        List<SysMenu> userMenuList = getUserMenusByUserId(userId);
        //创建一个保存用户按钮权限标识的List
        List<String> buttonPermissons = new ArrayList<>();
        //遍历权限菜单
        for (SysMenu sysMenu : userMenuList) {
            //将SysMenu的type值为2的perms的值放到buttonPermissons中
            if (sysMenu.getType() == 2) {
                buttonPermissons.add(sysMenu.getPerms());
            }
        }
        return buttonPermissons;
    }

    @Resource
    private SysMenuMapper sysMenuMapper;

    private List<SysMenu> getUserMenusByUserId(Long userId) {
//        需要有一个超级管理员没具有最大权限
        List<SysMenu> sysMenuList = null;
        if (userId == 1) {//超级管理员
            sysMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else {
            sysMenuList=sysMenuMapper.selectMenuListByUserId(userId);
        }
        return sysMenuList;
    }
}

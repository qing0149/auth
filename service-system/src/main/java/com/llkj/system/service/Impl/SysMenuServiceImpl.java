package com.llkj.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llkj.common.helper.MenuHelper;
import com.llkj.common.result.ResultCodeEnum;
import com.llkj.model.system.SysMenu;
import com.llkj.model.system.SysRoleMenu;
import com.llkj.model.vo.AssginMenuVo;
import com.llkj.system.exception.LlkjException;
import com.llkj.system.mapper.SysMenuMapper;
import com.llkj.system.mapper.SysRoleMenuMapper;
import com.llkj.system.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName SysMenuServiceImpl
 * @Description TODO
 * @Author qing
 * @Date 2022/12/24 8:35
 * @Version 1.0
 */
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> getMenusByRoleId(Long roledId) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        List<SysMenu> sysMenuList = this.list(wrapper);
        //获取当前角色的menu项的id数组
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper = new QueryWrapper<>();
        sysRoleMenuQueryWrapper.eq("role_id", roledId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(sysRoleMenuQueryWrapper);
        List<Long> menuIdList = new ArrayList<>();
//        只要其中的menu_id
        sysRoleMenuList.stream().forEach(sysRoleMenu -> {
            menuIdList.add(sysRoleMenu.getMenuId());
        });
        sysMenuList.stream().forEach(sysMenu -> {
            if (menuIdList.contains(sysMenu.getId())) {
                sysMenu.setSelect(true);
            } else {
                sysMenu.setSelect(false);
            }
        });
        //使用MenuHelper将并列的关系转换为权限树
        sysMenuList=MenuHelper.buildTree(sysMenuList);
        return sysMenuList;
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //删除之前所有的菜单向
        //update sys_rolemenu set is_deleted=1 where role_id=2
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assginMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);
        //增加当前角色现在分配的
        assginMenuVo.getMenuIdList().stream().forEach(menuId->{
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);
        });
    }

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> list = this.list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
//        将菜单列表转换为蚊拍列表，每个元素是树状的结构
        List<SysMenu> result = MenuHelper.buildTree(list);
        return result;
    }

    @Override
    public List<SysMenu> selectMenuListByUserId(Long userId) {
        return null;
    }

    @Override
    public boolean removeById(Serializable id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id", id);
        Integer count = sysMenuMapper.selectCount(wrapper);
        if (count > 0) {
            throw new LlkjException(ResultCodeEnum.NODE_ERROR);
        }

//        指定一个不存在id是成功还是失败
        int n = this.sysMenuMapper.deleteById(id);
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }
}

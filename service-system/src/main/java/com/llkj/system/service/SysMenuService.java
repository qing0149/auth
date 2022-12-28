package com.llkj.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llkj.model.system.SysMenu;
import com.llkj.model.vo.AssginMenuVo;

import java.util.List;

/**
 * @ClassName SysMenuService
 * @Description TODO
 * @Author qing
 * @Date 2022/12/24 8:34
 * @Version 1.0
 */
public interface SysMenuService extends IService<SysMenu> {
    /*
    获取指定角色的梳妆菜单列表
     */
    List<SysMenu> getMenusByRoleId(Long roledId);

    /*
    给指定角色分配权限
     */
    void doAssign(AssginMenuVo assginMenuVo);

    List<SysMenu> findNodes();

    List<SysMenu> selectMenuListByUserId(Long userId);
}

package com.llkj.system.controller;

import com.llkj.common.result.Result;
import com.llkj.model.system.SysMenu;
import com.llkj.model.vo.AssginMenuVo;
import com.llkj.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysMenuController
 * @Description 没有分页查询，只有获取node查询 ，有update没有getById 从页面拿去的数据
 * @Author qing
 * @Date 2022/12/24 8:46
 * @Version 1.0
 */
@RestController
@Api(tags = "菜单管理")
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单")
    @GetMapping("/findNodes")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu permission) {
        sysMenuService.save(permission);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "查询指定角色的信息id")
    @GetMapping("/toAssign/{roledId}")
    public Result toAssign(@PathVariable Long roledId) {
//        并列关系
        List<SysMenu> menuList=sysMenuService.getMenusByRoleId(roledId);
        return Result.ok(menuList);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }
}

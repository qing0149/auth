package com.llkj.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llkj.common.result.Result;
import com.llkj.model.system.SysUser;
import com.llkj.model.vo.AssginRoleVo;
import com.llkj.model.vo.SysUserQueryVo;
import com.llkj.system.mapper.SysUserMapper;
import com.llkj.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysUserController
 * @Description TODO
 * @Author qing
 * @Date 2022/12/20 23:16
 * @Version 1.0
 */
@RestController
@RequestMapping("admin/system/sysUser")
@Api(tags = "用户管理")
public class SysUserController {
    @Resource
    SysUserService sysUserService;

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign (@PathVariable Long userId){
        Map<String,Object> roleMap=sysUserService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        sysUserService.doAssign(assginRoleVo);
        return Result.ok();
    }


    @GetMapping("/updateStatus/{id}/{statues}")
    @ApiOperation(value = "修改用户状态")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer statues) {
        sysUserService.updateStatues(id, statues);
        return Result.ok();
    }

    @GetMapping("findAll")
    @ApiOperation(value = "查询全部")
    public Result findAll() {
        List<SysUser> sysUsers = sysUserService.list();
        return Result.ok(sysUsers);
    }

    @PutMapping("save")
    @ApiOperation(value = "保存用户")
    public Result save(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.ok();
    }

    @PostMapping("update")
    @ApiOperation(value = "修改用户")
    public Result updateById(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        return Result.ok();
    }

    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "删除用户")
    public Result reomve(@PathVariable String id) {
        sysUserService.removeById(id);
        return Result.ok();
    }


    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取用户")
    public Result get(@PathVariable String id) {
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "userQueryVo", value = "查询对象", required = false)
            SysUserQueryVo userQueryVo) {
        Page<SysUser> pageParam = new Page<>(page, limit);
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam, userQueryVo);
        return Result.ok(pageModel);
    }
}

package com.llkj.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llkj.common.result.Result;
import com.llkj.model.system.SysRole;
import com.llkj.model.vo.SysRoleQueryVo;
import com.llkj.system.exception.LlkjException;
import com.llkj.system.service.SysRoleService;
import com.mysql.cj.log.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysRoleController
 * @Description TODO
 * @Author qing
 * @Date 2022/12/16 14:11
 * @Version 1.0
 */
@RestController
@RequestMapping("admin/system/sysRole")
@Api(tags = "角色管理")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有角色")
    public Result<List<SysRole>> findAll() {
        List<SysRole> list = sysRoleService.list();
        try {
//            int i=1/0;
        } catch (Exception e) {
            LlkjException llkjException = new LlkjException(20001, "自定义异常" + e.getMessage());
            throw llkjException;
        }
        return Result.ok(list);
    }

    @GetMapping("get/{id}")
    @ApiOperation(value = "查询单个角色")
    public Result<SysRole> findOne(@PathVariable String id) {
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }


    @PostMapping("/save")
    @ApiOperation(value = "添加角色")
    public Result save(@RequestBody SysRole sysRole) {
        try {
            sysRoleService.save(sysRole);
            return Result.ok();
        } catch (Exception e) {
            return Result.fail();
        }
    }

    @PutMapping("updateById")
    @ApiOperation(value = "修改角色")
    public Result update(@RequestBody SysRole sysRole) {
        sysRoleService.updateById(sysRole);
        return Result.ok();
    }

    @DeleteMapping("/remove/{id}")
    @ApiOperation(value = "删除指定角色")
    public Result remove(@PathVariable @ApiParam("角色的ID") String id) {
        sysRoleService.removeById(id);
        return Result.ok();
    }

    @DeleteMapping("/batchRemove")
    @ApiOperation(value = "删除多个角色")
    public Result batchRemove(@RequestBody List<Long> idList) {
        sysRoleService.removeByIds(idList);
        return Result.ok();
    }
//    @ApiOperation("带条件分页查询1")
//    @GetMapping("s1/{page}/{limit}")
//    public Result findPages1(@PathVariable(value = "page") @ApiParam("当前页码") Long page,
//                            @PathVariable(value = "limit") @ApiParam("查询记录数") Long limit,
//                            @ApiParam("查询条件Bean") SysRoleQueryVo sysRoleQueryVo){
//        IPage<SysRole> ipage=new Page<>(page,limit);
//        QueryWrapper<SysRole> wrapper =new QueryWrapper<>();
//        if (sysRoleQueryVo.getRoleName()!=null){
//            wrapper.like("role_name",sysRoleQueryVo.getRoleName());
//        }
//        IPage<SysRole> result = sysRoleService.page(ipage, wrapper);
//        return Result.ok(result);
//    }

    @ApiOperation("带条件分页查询2")
    @GetMapping("{page}/{limit}")
    public Result findPages2(@PathVariable(value = "page") @ApiParam("当前页码") Long page,
                             @PathVariable(value = "limit") @ApiParam("查询记录数") Long limit,
                             @ApiParam("查询条件Bean") SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> ipage = new Page<>(page, limit);
        IPage<SysRole> result = sysRoleService.selectPage(ipage, sysRoleQueryVo);
        return Result.ok(result);
    }
}

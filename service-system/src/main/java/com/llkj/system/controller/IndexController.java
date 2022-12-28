package com.llkj.system.controller;

import com.llkj.common.helper.JwtHelper;
import com.llkj.common.result.Result;
import com.llkj.common.result.ResultCodeEnum;
import com.llkj.common.util.MD5;
import com.llkj.model.system.SysUser;
import com.llkj.model.vo.LoginVo;
import com.llkj.system.exception.LlkjException;
import com.llkj.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Guard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author qing
 * @Date 2022/12/19 9:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/system/index")
@Api("登录")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result login(@RequestBody LoginVo loginVo) {
        SysUser sysUser=sysUserService.getByUserName(loginVo.getUsername());
        if (null==sysUser){
            throw new LlkjException(ResultCodeEnum.ACCOUNT_ERROR);
        }
//        MD5是一种非对称的加斯算法，不可比较
        if (!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())){
            throw new LlkjException(ResultCodeEnum.PASSWORD_ERROR);
        }
        if (sysUser.getStatus().intValue()==0){
            throw new LlkjException(ResultCodeEnum.ACCOUNT_STOP);
        }
        Map<String,Object> map = new HashMap<>();

        map.put("token", JwtHelper.createToken(sysUser.getId(),sysUser.getUsername()));
        return Result.ok(map);
    }
    @GetMapping("info")
    public Result getInfo(HttpServletRequest request) {//请求头中携带令牌
        //获取令牌（令牌中有userID）
        String token = request.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        Map map=sysUserService.getUserInfoByUserId(userId);
        return Result.ok(map);
    }
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }

}

package com.llkj.system.controller;

import com.llkj.common.result.Result;
import com.llkj.model.vo.LoginVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api("")
public class IndexController {
    @PostMapping("login")
    public Result login(LoginVo loginVo) {
        Map map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);
    }
    @GetMapping("info")
    public Result getInfo() {
        Map map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","王小美");
        map.put("introduction","hahaha");
        map.put("avatar","https://pics0.baidu.com/feed/8d5494eef01f3a294366359b2cb922395d607c5a.jpeg@f_auto?token=c168cc04a485ee9432102e62e9648a5b");
        return Result.ok(map);
    }
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }

}

package com.llkj.system;

import com.llkj.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @ClassName SysRoleServiceTest
 * @Description TODO
 * @Author qing
 * @Date 2022/12/16 11:45
 * @Version 1.0
 */
@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    @Qualifier(value = "sysRoleServiceImpl")
//    @Resource
    SysRoleService sysRoleService;
    @Test
    public void test1(){
        int count = sysRoleService.count(null);
        System.out.println(count);

    }
}

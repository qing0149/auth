package com.llkj.system;

import com.llkj.model.system.SysRole;
import com.llkj.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SysRoleMapperTest
 * @Description TODO
 * @Author qing
 * @Date 2022/12/16 9:30
 * @Version 1.0
 */
@SpringBootTest
public class SysRoleMapperTest {
//    @Autowired(required = false)
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Test
    public void test1(){
//        System.out.println(sysRoleMapper);
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        System.out.println(sysRoles);
        SysRole sysRole = sysRoleMapper.selectById(1);
        System.out.println(sysRole);
    }
    @Test
    public void test2(){
        SysRole sysRole = sysRoleMapper.selectById(1);
        System.out.println(sysRole);
    }
    @Test
    public void test3(){
        SysRole sysRole = new SysRole();
//        sysRole.setId(100L);
        sysRole.setRoleName("游客1");
        sysRole.setRoleCode("ann");
        sysRole.setDescription("无");
        int insert = sysRoleMapper.insert(sysRole);
        System.out.println(insert);
    }
    @Test
    public void test4(){
        int i = sysRoleMapper.deleteById(1603580730728800257L);
    }

}

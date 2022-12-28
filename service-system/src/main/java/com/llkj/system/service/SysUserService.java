package com.llkj.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.llkj.model.system.SysUser;
import com.llkj.model.vo.AssginRoleVo;
import com.llkj.model.vo.SysUserQueryVo;
import com.llkj.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysUserService
 * @Description TODO
 * @Author qing
 * @Date 2022/12/20 23:18
 * @Version 1.0
 */
public interface SysUserService extends IService<SysUser> {
    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo adminQueryVo);

    void updateStatues(Long id, Integer statues);


    SysUser getByUserName(String username);

    Map getUserInfoByUserId(Long userId);
    List<String> getUserButtonPermissionsByUserId(Long userId);

}

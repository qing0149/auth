package com.llkj.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.llkj.model.system.SysRole;
import com.llkj.model.vo.SysRoleQueryVo;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysRoleService
 * @Description TODO
 * @Author qing
 * @Date 2022/12/16 11:40
 * @Version 1.0
 */

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo);


}

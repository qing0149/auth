package com.llkj.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llkj.model.system.SysMenu;

import java.util.List;

/**
 * @ClassName SysMenuMapper
 * @Description TODO
 * @Author qing
 * @Date 2022/12/24 8:33
 * @Version 1.0
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenuListByUserId(Long userId);
}

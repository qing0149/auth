package com.llkj.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llkj.model.system.SysUser;
import com.llkj.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName SysUserMapper
 * @Description TODO
 * @Author qing
 * @Date 2022/12/21 0:27
 * @Version 1.0
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(Page<SysUser> page, @Param("vo") SysUserQueryVo userQueryVo);

    void selectMenuListByUserId(Long userId);
}

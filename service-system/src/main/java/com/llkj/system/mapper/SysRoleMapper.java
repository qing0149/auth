package com.llkj.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llkj.model.system.SysRole;
import com.llkj.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName SysRoleMapper
 * @Description TODO
 * @Author qing
 * @Date 2022/12/16 9:27
 * @Version 1.0
 */
//@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper <SysRole>{
    IPage<SysRole> selectPage(Page<SysRole> page, @Param("vo") SysRoleQueryVo roleQueryVo);
}

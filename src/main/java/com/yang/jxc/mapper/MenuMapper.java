package com.yang.jxc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.jxc.domain.entity.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    @Select("SELECT id FROM menu WHERE parent_id != 0 AND id IN (SELECT menu_id FROM role_menu_relation  WHERE role_id= #{roleId})")
    List<Integer> queryMenuByRoleId(Long roleId);

    List<Menu> selectShowMenuByAdminName(String userName);

    List<Menu> selectChildMenuByAdminName(String userName, Long id);
}
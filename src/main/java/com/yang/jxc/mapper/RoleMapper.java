package com.yang.jxc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.jxc.domain.entity.Menu;
import com.yang.jxc.domain.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<Role> queryByUserId(Long userId);

    @Select("SELECT count(*) FROM admin_role_relation WHERE role_id = #{roleId}")
    int roleAndAdmin(Long roleId);

    @Select("delete from role_menu_relation where role_id = #{role_id}")
    void deleteRelationByRoleId(Long roleId);

    List<Role> getMenuList(Long adminId);

    List<Menu> getMenuListByRoleId(Long roleId);

    List<Menu> getResourceListByRoleId(Long roleId);

    List<Integer> queryroleByAdminId(Long adminId);
}
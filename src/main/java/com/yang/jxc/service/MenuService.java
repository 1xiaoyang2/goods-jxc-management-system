package com.yang.jxc.service;


import com.yang.jxc.domain.dto.ShowMenu;
import com.yang.jxc.domain.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * 菜单 service   前端暂时没有菜单功能
 */

public interface MenuService {

    /**
     * 添加菜单
     */
    int create(Menu menu);

    /**
     * 修改菜单信息
     */
    int update(Long id, Menu menu);

    /**
     * 批量删除菜单
     */
    int delete(List<Long> ids);

    /**
     * 获取所有菜单列表
     */
    List<Menu> list();

    /**
     * 分页获取角色列表
     */
    List<Menu> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 查询父菜单信息
     *
     * @param
     * @return
     */
    List<Menu> listMenuParent();

    /**
     * 查询子菜单信息
     *
     * @param id
     * @return
     */
    List<Menu> childMenu(Long id);

    List<Integer> queryMenuByRoleId(Long roleId);

    List<ShowMenu> selectShowMenuByAdminName();

    List<Map<String, Object>> listTreeMenu();

}

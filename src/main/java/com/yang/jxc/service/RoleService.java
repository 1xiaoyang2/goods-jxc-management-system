package com.yang.jxc.service;

import com.yang.jxc.domain.dto.RoleMenuRelationDTO;
import com.yang.jxc.domain.entity.Menu;
import com.yang.jxc.domain.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 后台角色管理Service
 */
public interface RoleService {

    /**
     * 通过id获取
     *
     * @param userId
     * @return
     */

    List<Role> queryByUserId(Long userId);

    /**
     * 添加角色
     */
    int create(Role role);

    /**
     * 修改角色信息
     */
    int update(Role role);

    /**
     * 批量删除角色
     */
    boolean delete(Long id);

    /**
     * 获取所有角色列表
     */
    List<Role> list();

    /**
     * 分页获取角色列表
     */

    List<Role> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<Role> getMenuList(Long adminId);

    /**
     * 获取角色相关菜单
     */
    List<Menu> listMenu(Long roleId);

    /**
     * 获取角色相关资源------------------暂时用menu
     */
    List<Menu> listResource(Long roleId);


    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);

    /**
     * 角色是后存在
     *
     * @param roleName
     * @return
     */
    boolean checkRoleName(String roleName);

    int addOrUpdateRole(Role role);


    Map<String, Object> getRoleMenuById(Long roleId);

    /**
     * 给角色分配菜单
     */

    int roleToAuth(RoleMenuRelationDTO roleMenu);


    List<Integer> getRoleByAdminId(Long adminId);

    List<Map<String, Object>> listTreeRole();

    List<Long> getMenuIdListByRoleId(Long roleId);
}

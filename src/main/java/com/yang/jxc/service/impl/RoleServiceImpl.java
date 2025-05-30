package com.yang.jxc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.yang.jxc.annotation.SystemLog;
import com.yang.jxc.constant.AopLogConstant;
import com.yang.jxc.domain.dto.RoleMenuRelationDTO;
import com.yang.jxc.domain.entity.Menu;
import com.yang.jxc.domain.entity.Purchase;
import com.yang.jxc.domain.entity.Role;
import com.yang.jxc.domain.entity.RoleMenuRelation;
import com.yang.jxc.mapper.RoleMapper;
import com.yang.jxc.mapper.RoleMenuRelationMapper;
import com.yang.jxc.service.MenuService;
import com.yang.jxc.service.RoleService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * role service层
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource //s使用 mbg中的
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuRelationMapper roleMenuRelationMapper;

    @Resource
    MenuService menuService;

    /**
     * 不实现
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> queryByUserId(Long userId) {
        return roleMapper.queryByUserId(userId);
    }

    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public int create(Role role) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userName = token.getPrincipal().toString();
        role.setBuildUser(userName);
        role.setCreateTime(LocalDateTime.now());
        return roleMapper.insert(role);
    }

    /**
     * 更新角色
     *
     * @param role
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_2)
    public int update(Role role) {
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.updateById(role);
    }

    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public boolean delete(Long roleId) {
        //删除角色信息   删除角色与菜单信息
        //  用户--角色   角色--菜单
        //后期判读 status是否正常
        int count = roleMapper.roleAndAdmin(roleId);
        if (count > 0) { //存在用户正在使用的角色则不能删除
            return false;
        } else {
            //删除 角色的同时删除和菜单的关系
            roleMapper.deleteRelationByRoleId(roleId);
            roleMapper.deleteById(roleId);
            return true;
        }
    }

    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public List<Role> list() {
        return roleMapper.selectList(new LambdaQueryWrapper<>());
    }

    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public List<Role> list(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Role::getRoleName, keyword);
        IPage<Role> page = new Page<>(pageNum, pageSize);
        return roleMapper.selectList(page, wrapper);
    }

    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public List<Role> getMenuList(Long adminId) {
        return roleMapper.getMenuList(adminId);
    }

    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public List<Menu> listMenu(Long roleId) {
        return roleMapper.getMenuListByRoleId(roleId);
    }

    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public List<Menu> listResource(Long roleId) {
        return roleMapper.getResourceListByRoleId(roleId);
    }


    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        return 0;
    }


    /**
     * 判断角色是否存在
     *
     * @param roleName
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public boolean checkRoleName(String roleName) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleName, roleName);
        return !CollectionUtils.isEmpty(roleMapper.selectList(wrapper));
    }


    /**
     * 添加 或更新角色
     *
     * @param role
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public int addOrUpdateRole(Role role) {
        //角色和菜单的关系
        // 判断 角色编号是否存在，如果存在就走更新的逻辑否则新增数据
        int res = 0;
        System.out.println(role.getRoleId());
        System.out.println(role.getRoleName());
        if (role.getRoleId() != null && role.getRoleId() != 0) {
            res = this.update(role);  // 表示更新操作
        } else {
            res = this.create(role);
        }
        return res;
    }

    /**
     * 查询角色分配的菜单信息
     *
     * @param roleId
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public Map<String, Object> getRoleMenuById(Long roleId) {
        List<Map<String, Object>> treeList = new ArrayList<>();
        /**
         * 1.查询所有的菜单信息
         *  1.查询父菜单信息
         */
        List<Menu> parents = menuService.listMenuParent();  //查询出一级菜单信息
        if (parents != null && !parents.isEmpty()) {
            for (Menu parent : parents) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", parent.getId());
                map.put("label", parent.getTitle());

                //根据父id查询子菜单
                Long id = parent.getId();
                List<Map<String, Object>> childList = new ArrayList<>();
                System.out.println("父菜单的id:" + id);
                List<Menu> childMenus = menuService.childMenu(id);
                if (childMenus != null && !childMenus.isEmpty()) {
                    for (Menu childMenu : childMenus) {
                        Map<String, Object> childmap = new HashMap<String, Object>();
                        childmap.put("id", childMenu.getId());
                        childmap.put("label", childMenu.getTitle());
                        childList.add(childmap);
                    }
                }
                //父子菜单关联
                map.put("children", childList);
                treeList.add(map);
            }
        }

        //根据角色编号查询分配的菜单编号 checkKey 前端的
        List<Integer> menuIds = menuService.queryMenuByRoleId(roleId);
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("checks", menuIds);
        resMap.put("treeData", treeList);
        return resMap;
    }

    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public int roleToAuth(RoleMenuRelationDTO roleMenu) {
        //先删除原有关系 角色-菜单关系表
        if (roleMenu.getRoleId() != null) {
            roleMapper.deleteById(roleMenu.getRoleId());
        }
        List<Long> menuIds = roleMenu.getMenuIds();
        if (menuIds != null && !menuIds.isEmpty()) {
            //批量插入新关系
            for (Long menuId : menuIds) {
                RoleMenuRelation relation = new RoleMenuRelation();
                relation.setRoleId(roleMenu.getRoleId());
                relation.setMenuId(menuId);
                roleMenuRelationMapper.insert(relation);
            }
        }
        return menuIds == null ? 0 : menuIds.size();
    }

    /**
     * 通过用户id获取所拥有的角色id
     *
     * @param adminId
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_2)
    @Override
    public List<Integer> getRoleByAdminId(Long adminId) {
        return roleMapper.queryroleByAdminId(adminId);
    }

    /**
     * 获取角色树
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listTreeRole() {
        List<Map<String, Object>> treeList = new ArrayList<>();
        /**
         * 1.查询所有的角色信息  封装为角色Tree
         */
        List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<>());
        if (ObjectUtil.isNotNull(roles)) {
            for (Role role : roles) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", role.getRoleId());
                map.put("label", role.getRoleName());
                treeList.add(map);
            }
        }
        return treeList;
    }


    /**
     * 获取菜单list
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<Long> getMenuIdListByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenuRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenuRelation::getRoleId, roleId);
        List<RoleMenuRelation> roleMenus = roleMenuRelationMapper.selectList(wrapper);

        ArrayList<Long> list = new ArrayList<>();
        for (RoleMenuRelation roleMenu : roleMenus) {
            list.add(roleMenu.getMenuId());
        }
        return list;
    }
}

package com.yang.jxc.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.yang.jxc.domain.dto.ShowMenu;
import com.yang.jxc.domain.entity.Menu;
import com.yang.jxc.domain.entity.Supplier;
import com.yang.jxc.mapper.MenuMapper;
import com.yang.jxc.service.MenuService;
import com.yang.jxc.utils.CommonPage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public int create(Menu menu) {
        menu.setCreateTime(LocalDateTime.now());
        //menu.setAdminCount(0);
        //  role.setSort(0);
        return menuMapper.insert(menu);
    }


    @Override
    public int update(Long id, Menu menu) {
        menu.setId(id);
        return menuMapper.updateById(menu);
    }

    @Override
    public int delete(List<Long> ids) {
        return menuMapper.deleteBatchIds(ids);
    }

    @Override
    public List<Menu> list() {
        return menuMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public CommonPage<Menu> list(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Menu::getName, keyword);
        IPage<Menu> page = new Page<>(pageNum, pageSize);
        IPage<Menu> menuIPage = menuMapper.selectPage(page, wrapper);
        return CommonPage.<Menu>builder().list(menuIPage.getRecords()).total(menuIPage.getTotal()).build();
    }

    /**
     * 查询所有的父菜单信息 一级菜单
     *
     * @return
     */
    @Override
    public List<Menu> listMenuParent() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, 0);
        wrapper.orderBy(true, true, Menu::getSort);
        return menuMapper.selectList(wrapper);
    }

    /**
     * 查询子菜单
     *
     * @param id 父菜单id
     * @return
     */
    @Override
    public List<Menu> childMenu(Long id) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, id);
        wrapper.orderBy(true, true, Menu::getSort);
        return menuMapper.selectList(wrapper);
    }

    @Override
    public List<Integer> queryMenuByRoleId(Long roleId) {
        return menuMapper.queryMenuByRoleId(roleId);
    }

    /**
     * 获取用户所具有的菜单
     *
     * @return
     */
    @Override
    public List<ShowMenu> selectShowMenuByAdminName() {
        //查询当前登陆用户的所有父菜单  用户--角色-->菜单
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) token.getPrincipal();
        //先查询出该用户具有的父菜单信息--->根据查询出的父菜单查询子菜单信息
        List<Menu> xtMenusList = menuMapper.selectShowMenuByAdminName(userName);
        if (xtMenusList != null && !xtMenusList.isEmpty()) {
            return xtMenusList.stream().map(item -> {  //父菜单
                ShowMenu showMenu = new ShowMenu();
                showMenu.setName(item.getName());
                showMenu.setLabel(item.getTitle());
                showMenu.setIcon(item.getIcon());
                showMenu.setUrl(item.getUrl());
                showMenu.setPath(item.getPath());
                //查询 该用户的二级菜单
                List<Menu> menuChildList = menuMapper.selectChildMenuByAdminName(userName, item.getId());
                if (menuChildList != null && !menuChildList.isEmpty()) {
                    List<ShowMenu> showChildMenus = new ArrayList<>();   //子菜单List
                    for (Menu menu : menuChildList) {       //每一个子菜单记录封装为一个对象
                        ShowMenu childMenu = new ShowMenu();   //Dto对象
                        childMenu.setName(menu.getName());
                        childMenu.setLabel(menu.getTitle());
                        childMenu.setIcon(menu.getIcon());
                        childMenu.setUrl(menu.getUrl());
                        childMenu.setPath(menu.getPath());
                        showChildMenus.add(childMenu);
                    }
                    showMenu.setChildren(showChildMenus);  //父菜单和子菜单封装
                }
                return showMenu;
            }).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 获取所有的菜单
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listTreeMenu() {
        List<Map<String, Object>> treeList = new ArrayList<>();
        /**
         * 1.查询所有的菜单信息 Tree
         *  1.查询父菜单信息
         */
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, 0);
        wrapper.orderBy(true, true, Menu::getSort);
        List<Menu> parents = menuMapper.selectList(wrapper);  //查询出一级菜单信息
        if (parents != null && !parents.isEmpty()) {
            for (Menu parent : parents) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", parent.getId());
                map.put("label", parent.getTitle());

                //根据父id查询子菜单
                Long id = parent.getId();
                List<Map<String, Object>> childList = new ArrayList<>();

                LambdaQueryWrapper<Menu> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(Menu::getParentId, id);
                wrapper1.orderBy(true, true, Menu::getSort);
                List<Menu> childMenus = menuMapper.selectList(wrapper1);
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
        return treeList;
    }
}
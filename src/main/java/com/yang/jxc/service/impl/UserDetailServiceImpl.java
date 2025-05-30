package com.yang.jxc.service.impl;

import com.yang.jxc.domain.entity.Admin;
import com.yang.jxc.domain.entity.Role;
import com.yang.jxc.service.AdminService;
import com.yang.jxc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证校验的方法
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    AdminService sysUserService;

    @Autowired
    RoleService sysRoleService;
    //前端访问 /login时进入此方法

    /**
     * 完成账号的校验
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    //是前端的 还是数据库中的？ username是前端的
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.需要根据账号查询
        List<Admin> list = sysUserService.queryByUserName(username);
        if (list != null && list.size() == 1) {
            // 账号是存在的
            Admin sysUser = list.get(0);
            // 根据当前登录的账号查询到关联的角色信息
            /**
             * 通过userid获取角色 （需要关系表查）
             */
            List<Role> sysRoles = sysRoleService.queryByUserId(sysUser.getId());
            List<GrantedAuthority> listRole = new ArrayList<>();
            for (Role sysRole : sysRoles) {
                listRole.add(new SimpleGrantedAuthority(sysRole.getRoleName()));
            }
            // 密码模拟的是就数据库查询出来  登陆的 不是真实的名
            return new User(sysUser.getUserName(), sysUser.getPassword(), listRole);
        }
        return null;
    }
}
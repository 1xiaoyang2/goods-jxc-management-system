package com.yang.jxc.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.yang.jxc.annotation.SystemLog;
import com.yang.jxc.constant.AopLogConstant;
import com.yang.jxc.domain.dto.AdminRoleRelationDTO;
import com.yang.jxc.domain.dto.UpdateAdminPasswordParam;
import com.yang.jxc.domain.entity.Admin;
import com.yang.jxc.domain.entity.AdminRoleRelation;
import com.yang.jxc.domain.entity.Role;
import com.yang.jxc.mapper.AdminMapper;
import com.yang.jxc.mapper.AdminRoleRelationMapper;
import com.yang.jxc.service.AdminService;
import com.yang.jxc.service.RoleService;
import com.yang.jxc.utils.CommonPage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RoleService roleService;


    @Resource
    private AdminRoleRelationMapper adminRoleRelationMapper;


    @Override
    public List<Admin> queryByUserName(String username) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUserName, username);
        return adminMapper.selectList(wrapper);
    }

    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public int create(Admin admin) {
        String TempPwd = "123456";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String TempEncode = bCryptPasswordEncoder.encode(TempPwd);
        admin.setPassword(TempEncode);
        return adminMapper.insert(admin);
    }

    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public int update(Admin admin) {
        //后期是否添加修改的时间
        return adminMapper.updateById(admin);
    }


    /**
     * 直接加密后存入  因为已经校验了
     *
     * @param param
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        String userName = param.getUsername();
        String newPassword = param.getNewPassword();
        String updatePassword = new BCryptPasswordEncoder().encode(newPassword);  //加密新密码
        LambdaUpdateWrapper<Admin> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Admin::getPassword, updatePassword);
        wrapper.eq(Admin::getUserName, userName);
        return adminMapper.update(wrapper);
    }


    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public int delete(Long id) {
        return adminMapper.deleteById(id);
    }


    /**
     * 这里使用list考虑到编程过程中的不唯一性
     *
     * @param userName
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public List<Admin> getAdminInfo(String userName) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUserName, userName);
        List<Admin> adminList = adminMapper.selectList(wrapper);
        for (Admin admin : adminList) {
            admin.setPassword(null);
        }
        return adminList;
    }

    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public boolean checkPassword(String username, String oldPassword) {
        boolean flag = false;
        //通过用户名查询用户密码，
        //对输入的加密后判断查询出的密码是否一致
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUserName, username);
        List<Admin> adminList = adminMapper.selectList(wrapper);
        String dbPassword = adminList.get(0).getPassword(); //获取数据库中的密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(oldPassword, dbPassword);
        if (matches) {
            flag = true;
        }
        System.out.println("密码校验结果：" + flag);
        return flag;
    }

    /**
     * 查询用户分配的角色信息
     *
     * @param adminId
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
        List<Map<String, Object>> treeList = new ArrayList<>();
        /**
         * 1.查询所有的角色信息
         */
        List<Role> roles = roleService.list();   //查询出所有的角色信息
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", role.getRoleId());
                map.put("label", role.getRoleName());
                treeList.add(map);
            }
        }
        //根据用户编号查询所拥有的角色编号
        List<Integer> menuIds = roleService.getRoleByAdminId(adminId);
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("checks", menuIds);
        resMap.put("treeData", treeList);
        return resMap;
    }

    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public int adminToRoleRelationship(AdminRoleRelationDTO adminRole) {
        //先删除原有关系 角色-菜单关系表
        if (adminRole.getAdminId() != null) {
            adminRoleRelationMapper.deleteById(adminRole.getAdminId());
        }
        List<Long> roleIds = adminRole.getRoleIds();
        if (!CollectionUtils.isEmpty(roleIds)) {
            //批量插入新关系
            for (Long roleId : roleIds) {
                AdminRoleRelation relation = new AdminRoleRelation();
                relation.setAdminId(adminRole.getAdminId());
                relation.setRoleId(roleId);
                adminRoleRelationMapper.insert(relation);
            }
        }
        return roleIds.size();
    }


    /**
     * 分页获取数据
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public CommonPage<Admin> getAdminList(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Admin::getUserName, keyword);
        IPage<Admin> page = new Page<>(pageNum, pageSize);
        IPage<Admin> adminIPage = adminMapper.selectPage(page, wrapper);
        return CommonPage.<Admin>builder().list(adminIPage.getRecords()).total(adminIPage.getTotal()).build();
    }

    /**
     * 用户名校验
     *
     * @param userName
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public boolean checkUserName(String userName) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUserName, userName);
        return !CollectionUtils.isEmpty(adminMapper.selectList(wrapper));
    }


    /**
     * 增加或者 修改
     *
     * @param admin
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public int updateOrAddById(Admin admin) {
        if (admin.getId() != null && admin.getId() != 0) {
            this.update(admin);
        } else {
            this.create(admin);
        }
        return 1;  //默认成功
    }


    /**
     * 密码初始化
     *
     * @param id
     * @return
     */
    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public int pwdInit(Long id) {
        String pwd = "123456";
        String initPwd = new BCryptPasswordEncoder().encode(pwd);  //加密密码
        LambdaUpdateWrapper<Admin> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Admin::getId, id);
        wrapper.set(Admin::getPassword, initPwd);
        return adminMapper.update(wrapper);
    }

    @SystemLog(AopLogConstant.XTMD_1)
    @Override
    public List<Map<String, Object>> getAdminAll() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        List<Admin> adminList = adminMapper.selectList(new LambdaQueryWrapper<>());
        if (adminList != null) {
            for (Admin admin : adminList) {
                Map<String, Object> hashMap = new HashMap<>();
                Long id = admin.getId();
                String userName = admin.getTrueName();  //获取真实姓名
                hashMap.put("id", id);
                hashMap.put("name", userName);
                list.add(hashMap);
            }
        }
        return list;
    }

    /**
     * 用户-角色关系表直接查询角色id
     *
     * @param adminId
     * @return
     */
    @Override
    public List<Long> getRoleIdListByUserId(Long adminId) {
        LambdaQueryWrapper<AdminRoleRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRoleRelation::getAdminId, adminId);
        List<AdminRoleRelation> userRoles = adminRoleRelationMapper.selectList(wrapper);

        ArrayList<Long> list = new ArrayList<>();
        for (AdminRoleRelation userRole : userRoles) {
            list.add(userRole.getRoleId());
        }
        return list;
    }
}


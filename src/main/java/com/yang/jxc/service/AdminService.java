package com.yang.jxc.service;

import com.yang.jxc.domain.dto.AdminRoleRelationDTO;
import com.yang.jxc.domain.dto.UpdateAdminPasswordParam;
import com.yang.jxc.domain.entity.Admin;
import com.yang.jxc.utils.CommonPage;

import java.util.List;
import java.util.Map;

/**
 * 用户service
 */

public interface AdminService{

    /**
     * 获取用户列表  因登陆名不唯一的话
     *
     * @param username
     * @return
     */
    List<Admin> queryByUserName(String username);

    /**
     * 添加用户信息
     *
     * @param Admin
     * @return
     */
    int create(Admin Admin);

    /**
     * 修改用户信息
     *
     * @param Admin
     * @return
     */
    int update(Admin Admin);

    /**
     * 修改用户密码
     *
     * @param updatePasswordParam
     * @return
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);


    /**
     * 用户名 昵称  分页获取列表
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    CommonPage<Admin> getAdminList(String keyword, Integer pageSize, Integer pageNum);

    boolean checkUserName(String userName);

    int updateOrAddById(Admin admin);

    int delete(Long id);

    /**
     * 获取登陆用户信息
     *
     * @return
     */
    List<Admin> getAdminInfo(String userName);

    boolean checkPassword(String username, String oldPassword);

    Map<String, Object> getRoleByAdminId(Long adminId);

    int adminToRoleRelationship(AdminRoleRelationDTO adminRole);

    int pwdInit(Long id);

    List<Map<String, Object>> getAdminAll();

    List<Long> getRoleIdListByUserId(Long userId);
}

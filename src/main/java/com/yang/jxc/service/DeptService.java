package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Dept;

import java.util.List;

public interface DeptService {

    /**
     * 添加部门
     */
    int create(Dept dept);

    /**
     * 修改部门信息
     */
    int update(Dept dept);

    /**
     * 批量删除部门
     */
    int delete(Long id);

    /**
     * 获取所有部门列表
     */
    List<Dept> list();

    /**
     * 分页获取部门列表
     */
    List<Dept> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 验证部门是否存在
     *
     * @param deptName
     * @return
     */
    boolean checkDeptName(String deptName);

    int updateOrAddById(Dept dept);
}

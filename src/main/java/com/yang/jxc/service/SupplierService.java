package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierService {

    /**
     * 添加客户
     */
    int create(Supplier supplier);

    /**
     * 修改客户信息
     */
    int update(Supplier supplier);

    /**
     * updateOrAddById 判断
     */
    int updateOrAddById(Supplier supplier);

    /**
     * 批量删除客户
     */
    int delete(Long id);

    /**
     * 获取所有客户
     */
    List<Supplier> list();

    /**
     * 分页获取客户列表
     */
    List<Supplier> list(String keyword, Integer pageNum, Integer pageSize);

    List<Map<String, String>> getNameAndAddress();
}

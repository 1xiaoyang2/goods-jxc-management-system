package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Customer;

import java.util.List;
import java.util.Map;

/**
 * long 是数据类型  Long是类
 */


public interface CustomerService {

    /**
     * 添加客户
     */
    int create(Customer customer);

    /**
     * 修改客户信息
     */
    int update(Customer customer);

    /**
     * 批量删除客户
     */
    int delete(long id);

    /**
     * 获取所有客户
     */
    List<Customer> list();

    /**
     * 分页获取客户列表
     */
    List<Customer> list(String keyword, Integer pageNum, Integer pageSize);


    List<Map<String, String>> getIDAndCustomerName();
}

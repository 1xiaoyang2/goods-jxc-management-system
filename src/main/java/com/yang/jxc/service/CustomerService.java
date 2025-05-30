package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Customer;
import com.yang.jxc.utils.CommonPage;

import java.util.List;
import java.util.Map;

/**
 * long 是数据类型  Long是类
 */


public interface CustomerService {

    /**
     * 添加或修改客户
     */
    int createOrUpdate(Customer customer);

    /**
     * 批量删除客户
     */
    int delete(long id);

    /**
     * 分页获取客户列表
     */
    CommonPage<Customer> list(String keyword, Integer pageNum, Integer pageSize);


    List<Map<String, String>> getIDAndCustomerName();
}

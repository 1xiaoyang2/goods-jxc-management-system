package com.yang.jxc.service;


import com.yang.jxc.domain.entity.SaleExit;

import java.util.List;

public interface SaleExitService {

    /**
     * 添加客户
     */
    int create(SaleExit saleExit);

    /**
     * 修改客户信息
     */
    int update(SaleExit saleExit);

    /**
     * 批量删除客户
     */
    int delete(Long id);

    /**
     * 获取所有客户
     */
    List<SaleExit> list();

    /**
     * 分页获取客户列表
     */
    List<SaleExit> list(String keyword, Integer pageSize, Integer pageNum);

    int addOrUpdate(SaleExit saleExit);
}

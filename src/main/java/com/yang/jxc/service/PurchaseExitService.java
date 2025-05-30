package com.yang.jxc.service;


import com.yang.jxc.domain.entity.PurchaseExit;
import com.yang.jxc.utils.CommonPage;

import java.util.List;

public interface PurchaseExitService {

    /**
     * 添加客户
     */
    int create(PurchaseExit purchaseExit);

    /**
     * 修改客户信息
     */
    int update( PurchaseExit purchaseExit);

    /**
     * 批量删除客户
     */
    int delete(Long id);

    /**
     * 获取所有客户
     */
    List<PurchaseExit > list();

    /**
     * 分页获取客户列表
     */
    CommonPage<PurchaseExit> list(String keyword, Integer pageSize, Integer pageNum);

    int addOrUpdate(PurchaseExit purchaseExit);
}

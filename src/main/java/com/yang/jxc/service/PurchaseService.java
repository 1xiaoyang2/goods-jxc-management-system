package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Purchase;
import com.yang.jxc.utils.CommonPage;

import java.util.List;
import java.util.Map;

public interface PurchaseService {

    /**
     * 添加客户
     */
    int create(Purchase purchase);

    /**
     * 修改客户信息
     */
    int update(Purchase purchase);

    /**
     * 批量删除客户
     */
    int delete(Long id);

    /**
     * 获取所有客户
     */
    List<Purchase> list();

    /**
     * 分页获取客户列表
     */
    CommonPage<Purchase> list(String keyword, Integer pageSize, Integer pageNum);

    int addOrUpdate(Purchase purchase);

    /**
     * 采购 入库操作
     *
     * @param
     * @return
     */
    int putStock(String depositoryName, Purchase purchaseList);

    List<Map<String, String>> getNumberAndSupplierName();

    int checkAndExitGoods(Purchase purchase);
}
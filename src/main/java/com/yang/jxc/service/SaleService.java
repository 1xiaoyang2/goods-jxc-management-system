package com.yang.jxc.service;



import com.yang.jxc.domain.entity.Sale;

import java.util.List;
import java.util.Map;

public interface SaleService {

    /**
     * 添加客户
     */
    int create(Sale sale);

    /**
     * 修改客户信息
     */
    int update(Sale sale);

    /**
     * 批量删除客户
     */
    int delete(Long id);

    /**
     * 获取所有客户
     */
    List<Sale > list();

    /**
     * 分页获取客户列表
     */
    List<Sale> list(String keyword, Integer pageSize, Integer pageNum);

    int addOrUpdate(Sale sale);

    List<Map<String, String>> getNumberAndCustomer();

    //销售 出库
    int outSaleAndCheck(Map<String,Object> map);

    int checkInGoods(Map<String, Object> map);
}

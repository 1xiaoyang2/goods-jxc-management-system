package com.yang.jxc.service;



import com.yang.jxc.domain.entity.Sale;
import com.yang.jxc.utils.CommonPage;

import java.util.List;
import java.util.Map;

public interface SaleService {
    /**
     * 批量删除客户
     */
    int delete(Long id);

    /**
     * 分页获取客户列表
     */
    CommonPage<Sale> list(String keyword, Integer pageSize, Integer pageNum);

    int addOrUpdate(Sale sale);

    List<Map<String, String>> getNumberAndCustomer();

    //销售 出库
    int outSaleAndCheck(Map<String,Object> map);

    int checkInGoods(Map<String, Object> map);
}

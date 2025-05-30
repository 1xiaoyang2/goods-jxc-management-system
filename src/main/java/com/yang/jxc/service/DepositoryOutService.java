package com.yang.jxc.service;


import com.yang.jxc.domain.entity.DepositoryOut;
import com.yang.jxc.utils.CommonPage;

import java.util.List;

public interface DepositoryOutService {


    /**
     * 添加出库清单
     */
    int create(DepositoryOut depositoryOut);

    /**
     * 修改出库清单
     */
    int update(DepositoryOut depositoryOut);

    /**
     * 批量删除出库清单
     */
    int delete(Long id);

    /**
     * 获取所有出库清单列表
     */
    List<DepositoryOut> list();

    /**
     * 分页获取出库清单列表
     */
    CommonPage<DepositoryOut> list(String keyword, Integer pageSize, Integer pageNum);


    int addOrUpdateDepositoryOut(DepositoryOut DepositoryOut);

    int checkById(Long id);

    /**
     * 通过编号查询 source_number
     */
    List<DepositoryOut> getRowInfoByPurchaseNumber(int number);
}

package com.yang.jxc.service;


import com.yang.jxc.domain.entity.DepositoryIn;

import java.util.List;

public interface DepositoryInService {


    /**
     * 添加入库清单
     */
    int create(DepositoryIn depositoryIn);

    /**
     * 修改入库清单
     */
    int update(DepositoryIn depositoryIn);

    /**
     * 批量删除入库清单
     */
    int delete(Long id);

    /**
     * 获取所有入库清单列表
     */
    List<DepositoryIn> list();

    /**
     * 分页获取入库清单列表
     */
    List<DepositoryIn> list(String keyword, Integer pageSize, Integer pageNum);


    boolean checkDepositoryInId(Long depositoryInId);

    int addOrUpdateDepositoryIn(DepositoryIn depositoryIn);

    int checkById(Long id);

    List<DepositoryIn> getRowInfoByPurchaseNumber(int number);
}

package com.yang.jxc.service;


import com.yang.jxc.domain.dto.StockUpDTO;
import com.yang.jxc.domain.entity.Depository;

import java.util.List;
import java.util.Map;

/**
 * 仓库 service
 */
public interface DepositoryService {

    /**
     * 添加仓库
     */
    int create(Depository depository);

    /**
     * 修改仓库信息
     */
    int update(Depository depository);

    /**
     * 删除仓库
     */
    int delete(Long id);

    /**
     * 获取所有仓库列表
     */
    List<Depository> list();

    /**
     * 分页获取仓库列表
     */
    List<Depository> list(String keyword, Integer pageSize, Integer pageNum);


    boolean checkUserName(String name);

    int updateOrAddById(Depository depository);

    /**
     * 更新 仓库的容量
     */
    int updateAreaByTwoName(StockUpDTO stockUpDto);

    List<Map<String, String>> getDepositoryToEChart();

    int getLength();
}

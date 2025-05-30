package com.yang.jxc.service;

import com.yang.jxc.domain.dto.StockUpDTO;
import com.yang.jxc.domain.entity.Stock;
import com.yang.jxc.utils.CommonPage;

import java.util.List;
import java.util.Map;

/**
 * 仓库-库存 service
 */

public interface StockService {

    /**
     * 添加库存
     */
    int create(Stock stock);

    /**
     * 修改库存信息-------无
     */
    int update(Stock stock);

    /**
     * 删除库存
     */
    int delete(Long id);

    /**
     * 获取所有库存列表
     */
    List<Stock> list();

    /**
     * 分页获取库存列表
     */
    CommonPage<Stock> list(String keyword, Integer pageSize, Integer pageNum);

    int addOrUpdateStock(Stock stock);

    /**
     * 更新库存列表的库存数据
     * @param
     * @param
     * @return
     */
    int updateQuantityByTwoName(StockUpDTO stockUpDto);

    /**
     * 通过商品和仓库名获取库存列表
     * @param shopName
     * @param depositoryName
     * @return
     */
    List<Stock> getStockByTwoName(String shopName, String depositoryName);


    /**  柱状图
     * 商品1：[采购量   销售量、 库存量 ]
     * @return
     */
    List<Map<String, List<?>>> histogramList();


    /**
     *  饼图
     * @return
     */
    List shopMap();
}

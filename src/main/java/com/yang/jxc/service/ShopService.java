package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Shop;
import com.yang.jxc.domain.entity.ShopType;
import com.yang.jxc.utils.CommonPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ShopService {

    /**
     * 添加客户
     */
    int create(Shop shop);

    /**
     * 修改客户信息
     */
    int update(Shop shop);


    /**
     * 判断是添加还是修改
     */
    int updateOrAddById(Shop shop);


    /**
     * 删除客户
     */
    int delete(Long id);

    /**
     * 获取所有客户
     */
    List<Shop> list();

    /**
     * 分页获取客户列表
     */
    CommonPage<Shop> list(String keyword, Integer pageNum, Integer pageSize);


    /**
     * 获取商品类型
     *
     * @param shopName
     * @return
     */
    ShopType selectShopTypeByName(String shopName);

    ArrayList<Map<String, Object>> getShopNameAll();

    List<ShopType> selectShopTypeList();

//     ShopTypeDTO  selectShopTypeList();
}

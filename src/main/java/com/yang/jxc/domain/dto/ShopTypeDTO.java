package com.yang.jxc.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShopTypeDTO {
    /**
     * 【商品类型   采购量   销售量   库存量】
     */
    private List<String>  shopTypeList ;  //商品类型

    private List<Long> saleList;    //采购量

    private List<Long> stockList;   //库存量
}

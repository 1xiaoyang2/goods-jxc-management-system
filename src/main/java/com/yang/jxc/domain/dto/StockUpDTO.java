package com.yang.jxc.domain.dto;

import lombok.Data;

/**
 * 商品库存更新封装
 *
 */
@Data
public class StockUpDTO {

    private  int flag;  //表示0 增加

    private  String shopName;  //商品名称

    private  String depositoryName;  //仓库名称

    private  Long quantity;  //数量
}

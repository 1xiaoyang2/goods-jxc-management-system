package com.yang.jxc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.jxc.domain.dto.ShopAndSaleAndStockAndPurchaseDTO;
import com.yang.jxc.domain.entity.Stock;

import java.util.List;

public interface StockMapper extends BaseMapper<Stock> {
    List<ShopAndSaleAndStockAndPurchaseDTO> getNumberToShopAndStockAndPurchase();
}
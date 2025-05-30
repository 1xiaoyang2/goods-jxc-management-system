package com.yang.jxc.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.yang.jxc.domain.dto.ShopAndSaleAndStockAndPurchaseDTO;
import com.yang.jxc.domain.dto.StockUpDTO;
import com.yang.jxc.domain.entity.Depository;
import com.yang.jxc.domain.entity.Shop;
import com.yang.jxc.domain.entity.Stock;
import com.yang.jxc.mapper.DepositoryMapper;
import com.yang.jxc.mapper.SaleMapper;
import com.yang.jxc.mapper.StockMapper;
import com.yang.jxc.service.StockService;
import com.yang.jxc.utils.CalculationUtil;
import com.yang.jxc.utils.CommonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存列表
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired     
    private StockMapper stockMapper;

    @Autowired
    private SaleMapper saleMapper;    //销售表

    @Autowired
    private DepositoryMapper depositoryMapper;   //仓库列表

    @Override
    public int create(Stock stock) {
        stock.setCreateTime(LocalDateTime.now());
        //menu.setAdminCount(0);
        //  role.setSort(0);
        return stockMapper.insert(stock);
    }

    @Override
    public int update(Stock stock) {
        stock.setUpdateTime(LocalDateTime.now());
        return stockMapper.updateById(stock);
    }

    @Override
    public int delete(Long id) {
        return stockMapper.deleteById(id);
    }

    @Override
    public List<Stock> list() {
        return stockMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public CommonPage<Stock> list(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Stock::getShop, keyword);
        IPage<Stock> page = new Page<>(pageNum, pageSize);
        IPage<Stock> stockIPage = stockMapper.selectPage(page, wrapper);
        return CommonPage.<Stock>builder().list(stockIPage.getRecords()).total(stockIPage.getTotal()).build();
    }

    @Override
    public int addOrUpdateStock(Stock stock) {
        // 判断  主键是否存在，如果存在就走更新的逻辑否则新增数据
        /**
         *  增加出现 ：商品名称和 存入库存相同的情况
         *  通过 传入的 商品名称和 存入库存是否和数据库中有相同的    有则更新数据库中的，没有则添加
         */
        int res = 0;
        if (stock.getId() != null && stock.getId() != 0) { //有具体id
            // 表示更新操作
            return this.update(stock);
        }
        return this.create(stock);
    }


    //            更新库存列表的库存数据
    @Override
    public int updateQuantityByTwoName(StockUpDTO stockUpDto) {
        LambdaUpdateWrapper<Stock> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Stock::getDepository, stockUpDto.getDepositoryName());
        wrapper.eq(Stock::getShop, stockUpDto.getShopName());
        wrapper.setSql("quantity = quantity" + (stockUpDto.getFlag() == 0 ? "+" : "-") + stockUpDto.getQuantity());
        return stockMapper.update(wrapper);
    }

    /**
     * 通过商品和仓库名获取库存列表
     *
     * @param shopName
     * @param depositoryName
     * @return
     */
    @Override
    public List<Stock> getStockByTwoName(String shopName, String depositoryName) {
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Stock::getShop, shopName);
        wrapper.eq(Stock::getDepository, depositoryName);
        return stockMapper.selectList(wrapper);
    }

    /**
     * Echart地图 柱状图数据
     * 商品1：[采购量   销售量、 库存量 ]  销售总价
     * 三张表 关联统计查询  左连接表 去匹配 三张表  1:n的关系
     * <p>
     * 销售表  1.获取销售量最高的前5个商品  => 5个商品 + 5个销售量
     * 库存表      库存量 查询统计5个商品的库存量，条件是商品名  ==> 库存量
     * 采购表   采购表    查询统计5个商品的采购，条件是商品名  ==>  采购量
     * <p>
     *
     * @return
     */
    @Override
    public List<Map<String, List<?>>> histogramList() {
        List<Map<String, List<?>>> allList = new ArrayList<>();
        List<String> shopList = new ArrayList<>();      //商品集合
        List<Long> saleList = new ArrayList<>();       //销售量集合
        List<Long> stockList = new ArrayList<>();      //库存量集合
        List<Long> purchaseList = new ArrayList<>();     //采购量集合
        Map<String, List<?>> map = new HashMap<>();
        Long base = 0L;
        List<ShopAndSaleAndStockAndPurchaseDTO> histogramData = stockMapper.getNumberToShopAndStockAndPurchase();
        if (histogramData != null) {
            for (ShopAndSaleAndStockAndPurchaseDTO dto : histogramData) {
                // 商品list  销售量list  库存量list  采购量list
                String shopName = dto.getShopName();
                Long stockNumber = dto.getStockNumber();
                Long saleNumber = dto.getSaleNumber();
                Long purchaseNumber = dto.getPurchaseNumber();
                shopList.add(shopName);
                //判断 是否为空 销售、库存、采购
                if (stockNumber != null && !stockNumber.toString().isEmpty()) {
                    stockList.add(stockNumber);
                } else {
                    stockList.add(base);
                }
                if (saleNumber != null && !saleNumber.toString().isEmpty()) {
                    saleList.add(dto.getSaleNumber());
                } else {
                    saleList.add(base);
                }
                if (purchaseNumber != null && !purchaseNumber.toString().isEmpty()) {
                    purchaseList.add(dto.getPurchaseNumber());
                } else {
                    purchaseList.add(base);
                }
            }
        }
        map.put("shopList", shopList);  //商品
        map.put("saleList", saleList); //销售量
        map.put("stockList", stockList);  //库存量
        map.put("purchaseList", purchaseList);  //采购量
        allList.add(map);
        return allList;  //需要处理
    }


    /**
     * 饼图 商品库存列表
     * 库存   k:商品  value: 公式计算 库存量 / 总仓库容量
     *
     * @return
     */
    @Override
    public List<Map> shopMap() {
        List<Map> list = new ArrayList<>();
        //查询仓库总容量
        Long totalVolume = 0L;
        List<Depository> depositoryList = depositoryMapper.selectList(new LambdaQueryWrapper<>());
        for (Depository depository : depositoryList) {
            Long stockTotal = depository.getStockTotal();
            totalVolume += stockTotal;
        }
        //计算  容量转为kg
        BigDecimal bigKg = CalculationUtil.INTCalculatingVolumeToWeight(totalVolume);
        //获取商品库存列表
        List<Stock> stockList = stockMapper.selectList(new LambdaQueryWrapper<>());
        for (Stock stock : stockList) {
            String shop = stock.getShop(); //商品名
            Long quantity = stock.getQuantity(); //库存量
            BigDecimal bigDecimal = new BigDecimal(quantity);
            BigDecimal divideValue = bigDecimal.divide(bigKg, 0);   // 除后的
            if (10 > divideValue.signum()) {
                divideValue = BigDecimal.valueOf(10);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("name", shop);
            map.put("value", String.valueOf(divideValue));
            list.add(map);
        }
        return list;
    }
}

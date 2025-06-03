package com.yang.jxc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.DepositoryOut;
import com.yang.jxc.domain.entity.PurchaseExit;
import com.yang.jxc.domain.entity.Stock;
import com.yang.jxc.mapper.DepositoryMapper;
import com.yang.jxc.mapper.DepositoryOutMapper;
import com.yang.jxc.mapper.PurchaseExitMapper;
import com.yang.jxc.mapper.StockMapper;
import com.yang.jxc.service.DepositoryOutService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.UUidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 入库清单 service
 */
@Service
public class DepositoryOutServiceImpl implements DepositoryOutService {

    @Autowired
    private DepositoryOutMapper depositoryOutMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
        private DepositoryMapper depositoryMapper;
    @Autowired
    private PurchaseExitMapper purchaseExitMapper;


    @Override
    public int create(DepositoryOut depositoryOut) {
        depositoryOut.setOutId(Long.valueOf(UUidUtils.uuid()));
        depositoryOut.setCreateDate(LocalDateTime.now());
        depositoryOut.setStatus(1); //默认出库
        /**
         * 计算
         （1）小数计算对精度无要求时，使用float节省时间。
         （2）如果有精度要求，用BigDecimal类处理（初始化必须使用字符串，因为用数值初始化会得到近似值，不准确），
         然后设置保留位数和 舍入法（half_up四舍五入，half_even银行家，half_down向下取整）
         */
        BigDecimal priceTotal = null;
        BigDecimal shopPrice = depositoryOut.getShopPrice();  //商品单价
        Long shopNumber = depositoryOut.getShopNumber();    //商品数量
        if (shopPrice != null && shopNumber != null) {
            BigDecimal ShopNumber1 = new BigDecimal(shopNumber);
            priceTotal = ShopNumber1.multiply(shopPrice);  //商品数量乘商品价格
            depositoryOut.setTotalPrice(priceTotal);
        }

        return depositoryOutMapper.insert(depositoryOut);
    }

    @Override
    public int update(DepositoryOut depositoryOut) {
        BigDecimal priceTotal = null;
        BigDecimal shopPrice = depositoryOut.getShopPrice();  //商品单价
        Long shopNumber = depositoryOut.getShopNumber();    //商品数量
        if (shopPrice != null && shopNumber != null) {
            BigDecimal ShopNumber1 = new BigDecimal(shopNumber);
            priceTotal = ShopNumber1.multiply(shopPrice);  //商品数量乘商品价格
            depositoryOut.setTotalPrice(priceTotal);
        }
        return depositoryOutMapper.updateById(depositoryOut);
    }

    @Override
    public int delete(Long id) {
        return depositoryOutMapper.deleteById(id);
    }

    @Override
    public List<DepositoryOut> list() {
        return depositoryOutMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public CommonPage<DepositoryOut> list(String keyword, Integer pageSize, Integer pageNum) {
        if(keyword != null){
            keyword = keyword.trim();
        }
        LambdaQueryWrapper<DepositoryOut> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), DepositoryOut::getShopName, keyword);
        IPage<DepositoryOut> page = new Page<>(pageNum, pageSize);
        IPage<DepositoryOut> depositoryOutIPage = depositoryOutMapper.selectPage(page, wrapper);
        return CommonPage.<DepositoryOut>builder().list(depositoryOutIPage.getRecords()).total(depositoryOutIPage.getTotal()).build();
    }

    @Override
    public int addOrUpdateDepositoryOut(DepositoryOut depositoryOut) {
        // 判断 编号是否存在，如果存在就走更新的逻辑否则新增数据
        int res = 0;
        if (depositoryOut.getId() != null && depositoryOut.getId() != 0) {
            // 表示更新操作
            res = this.update(depositoryOut);
        } else {
            res = this.create(depositoryOut);
        }
        return res;
    }


    /**
     * 商品 审核后出库
     * 0. 库存列表是否有充足的库存？ 无则返回  有则执行1
     * 1. 判断是否重复出库， 否 则执行2
     * 2. 出库 且 增加对应的仓库容量，及 减少对应的库存容量
     */
    @Override
    public int checkById(Long id) {
        int result = 0;
        if (id == null) return result;
        DepositoryOut upDepositoryOut = new DepositoryOut();
        /**
         *  //通过id查询信息中是否出库这个字段 如果已经出库，就不需要更新出库时间了
         */

        DepositoryOut selectDepositoryOut = depositoryOutMapper.selectById(id);  //原来的状态
        String shopName = selectDepositoryOut.getShopName();  //商品名
        String depositoryName = selectDepositoryOut.getDepository();  //获取仓库名
        Long shopNumber = selectDepositoryOut.getShopNumber();   //获取出库的数量
        Long sourceNumber = selectDepositoryOut.getSourceNumber(); //获取采购编号
        //======> 增加时 必须有 商品名 仓库名 出库数量
        /**
         * 是否有库存   库存的 ？  需要出库的
         */
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Stock::getShop, shopName);
        wrapper.eq(Stock::getDepository, depositoryName);
        List<Stock> stockList = stockMapper.selectList(wrapper);
        Stock stockCheck = stockList.get(0);
        //如果库存少于需要出库的 直接返回
        if (stockCheck.getQuantity() < shopNumber) {
            return result;
        }

        if (selectDepositoryOut.getStatus() == 1) {  //原来还是未出库状态则设置 出库时间
            upDepositoryOut.setDate(LocalDateTime.now());
        }
        upDepositoryOut.setOutInspection(0);      //审核  状态
        upDepositoryOut.setStatus(0);            //出库   状态
        //更新的条件
        result = depositoryOutMapper.updateById(upDepositoryOut);
        //进行中且 更新完成
        if (selectDepositoryOut.getStatus() == 1 && result > 0) {
            /**
             *   出库单更新成功后才能更新库存
             * 更新(减少)所对应的商品-仓库的容量(商品库存列表)
             */
            /**
             * 仓库容量变化       //剩余量  >  总量  怎么办 ？
             */

            //先查询采购退货是否有采购编号
            LambdaQueryWrapper<PurchaseExit> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(PurchaseExit::getNumber, sourceNumber);
            List<PurchaseExit> purchaseExitNumber = purchaseExitMapper.selectList(wrapper1);
            if (!ObjectUtil.isEmpty(purchaseExitNumber.get(0))) {  //空 true   ; 非空则执行
                /**
                 * 通过出库编号查询 源编号(采购编号)
                 *    查询是否有这个采购编号的信息
                 * 然后更新 采购退货中的信息
                 * 更新采购退货状态
                 */
                LambdaUpdateWrapper<PurchaseExit> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(PurchaseExit::getNumber, sourceNumber);
                updateWrapper.set(PurchaseExit::getStatus, 0);
                updateWrapper.set(PurchaseExit::getRemark, "采购->入库->退购-->出库-->完成出库成功");
                purchaseExitMapper.update(updateWrapper);
            }
        }


        return result;
    }

    /**
     * 通过 编号 获取 出库清单
     *
     * @param number 源 编号
     * @return
     */
    @Override
    public List<DepositoryOut> getRowInfoByPurchaseNumber(int number) {
        LambdaQueryWrapper<DepositoryOut> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepositoryOut::getSourceNumber, number);
        return depositoryOutMapper.selectList(wrapper);
    }
}

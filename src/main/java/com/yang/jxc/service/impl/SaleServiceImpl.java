package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.*;
import com.yang.jxc.mapper.DepositoryOutMapper;
import com.yang.jxc.mapper.SaleExitMapper;
import com.yang.jxc.mapper.SaleMapper;
import com.yang.jxc.mapper.StockMapper;
import com.yang.jxc.service.DepositoryOutService;
import com.yang.jxc.service.SaleService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.UUidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门  service
 */
@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private DepositoryOutMapper depositoryOutMapper;  //出库

    @Autowired
    private DepositoryOutService depositoryOutService;  //服务层
    @Autowired
    private SaleExitMapper saleExitMapper;

    @Autowired
    private StockMapper stockMapper;  //库存清单

    @Override
    public int delete(Long id) {
        return saleMapper.deleteById(id);
    }

    @Override
    public CommonPage<Sale> list(String keyword, Integer pageSize, Integer pageNum) {
        if(keyword != null){
            keyword = keyword.trim();
        }
        LambdaQueryWrapper<Sale> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Sale::getSaleUser, keyword);
        IPage<Sale> page = new Page<>(pageNum, pageSize);
        IPage<Sale> saleIPage = saleMapper.selectPage(page, wrapper);
        return CommonPage.<Sale>builder().list(saleIPage.getRecords()).total(saleIPage.getTotal()).build();
    }

    @Override
    public int addOrUpdate(Sale sale) {
        // 判断 编号是否存在，如果存在就走更新的逻辑否则新增数据
        int res = 0;
        if (sale.getId() != null && sale.getId() != 0) {
            // 表示更新操作
            res = this.update(sale);
        } else {
            res = this.create(sale);
        }
        return res;
    }
    public int create(Sale sale) {
        sale.setTime(LocalDateTime.now());
        BigDecimal totalPrice = null;
        BigDecimal price = sale.getPrice();
        Long num = sale.getNum();
        if (price != null && num != null) {
            BigDecimal bigDecimal = new BigDecimal(num);  //其他类型转BigDecimal需要new来转化
            totalPrice = bigDecimal.multiply(price);
            sale.setTotalPrice(totalPrice); //计算总价
        }
        sale.setStatus(1); //默认进行中
        sale.setSaleNumber(String.valueOf(UUidUtils.uuid())); //销售编号uuid
        return saleMapper.insert(sale);
    }

    public int update(Sale sale) {
        sale.setTotalPrice(sale.getPrice().multiply(BigDecimal.valueOf(sale.getNum())));
        //传入字段为null不更新数据库字段
        return saleMapper.updateById(sale);
    }


    /**
     * 获取 销售编号和客户
     *
     * @return
     */
    @Override
    public List<Map<String, String>> getNumberAndCustomer() {
        List<Map<String, String>> list = new ArrayList<>();
        List<Sale> supplierList = saleMapper.selectList(new LambdaQueryWrapper<>());
        if (supplierList != null) {
            for (Sale sale : supplierList) {
                String customer = sale.getSupplier(); // 客户
                String number = sale.getSaleNumber(); //编号
                //编号不能为空
                if (number != null && !number.equals("")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("value", number);
                    map.put("customer", customer);
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * 出库
     *
     * @param map
     * @return
     */
    @Override
    public int outSaleAndCheck(Map<String, Object> map) {
        String depositoryName = map.get("depositoryName").toString();
        Sale saleList = (Sale) map.get("saleList");

        /**
         * 避免重复出库
         *   查询出库清单中是否已经存在
         */
        List<DepositoryOut> rowInfoByPurchaseNumber = depositoryOutService.getRowInfoByPurchaseNumber(Integer.parseInt(saleList.getSaleNumber()));
        if (!CollectionUtils.isEmpty(rowInfoByPurchaseNumber)) { //证明已 出库
            return 2;  //不能重复出库
        }
        /**
         *   1. 销售出库  该仓库是否拥有该商品，且数量是否充足。
         *   2. 出库 生成出库清单，(不需要改库存变化，统一审核后更改  或者审核时校验是否有充足的库存，审核成功则需更改销售状态为完成)
         */
        //  1 库存单
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Stock::getDepository, depositoryName);
        wrapper.eq(Stock::getShop, saleList.getShop());
        List<Stock> stockList = stockMapper.selectList(wrapper);
        Stock stock = stockList.get(0);

        //2       库存充足-----> 生成出库清单
        if (stock != null && stock.getQuantity() >= saleList.getNum()) {
            DepositoryOut depositoryOut = new DepositoryOut();
            depositoryOut.setSourceNumber(Long.valueOf(saleList.getSaleNumber()));  //源 编号
            depositoryOut.setOutId(Long.valueOf(UUidUtils.uuid()));  //编号
            depositoryOut.setDepository(stock.getDepository());  //仓库
            depositoryOut.setShopName(saleList.getShop()); //商品
            depositoryOut.setShopNumber(saleList.getNum());  //数量
            depositoryOut.setShopPrice(saleList.getPrice());  //价格
            depositoryOut.setTotalPrice(saleList.getTotalPrice()); //总价
            depositoryOut.setSpecs(saleList.getSpecs());  //规格
            // depositoryOut.setDate(LocalDateTime.now()); //出库时间  审核后写
            UsernamePasswordAuthenticationToken token =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String userName = token.getPrincipal().toString();
            depositoryOut.setOutUser(userName);  //出库人
            depositoryOut.setShopSupplier(saleList.getSupplier()); // 客户
            depositoryOut.setStatus(1); //是否出库 1   需审核
            depositoryOut.setOutInspection(1);  //未审核
            depositoryOut.setCreateDate(LocalDateTime.now());  //创建时间
            depositoryOutMapper.insert(depositoryOut);  //生成出库单
        } else {
            return 1;  //出库失败
        }
        return 0;  //成功
    }

    /**
     * 销售    回收   2023-6.2
     *
     * @param map
     * @return
     */
    @Override
    public int checkInGoods(Map<String, Object> map) {
        String remark = map.get("remark").toString();   //销售退货原因
        Sale saleList = (Sale) map.get("saleList");  //销售信息
        /**
         * 通过销售编号查询 是否已经存在销售退货订单了  无：返回
         */
        LambdaQueryWrapper<SaleExit> saleExitWrapper = new LambdaQueryWrapper<>();
        saleExitWrapper.eq(SaleExit::getExitNumber, saleList.getSaleNumber());
        List<SaleExit> saleExits = saleExitMapper.selectList(saleExitWrapper);
        if (saleExits != null) return 1; // 存在则直接返回
        /**
         *      1. 状态为0/1 都生成 销售退货单，并添加退货原因|| 销售单是进行中：无其他操作  销售退货单状态为完成0；而外生成 生成入库清单
         *      2.判断是否出库（出库-就需要生成：【销售退货+入库清单】，未出库则直接生成：【销售退货】），
         *          判断其状态是否完成 （完成：表示已到客户手中，需要【入库清单，销售退货单】； 未完成：直接生成【销售退货单】）
         */
        /**
         * 1
         */
        SaleExit saleExitEntity = new SaleExit();
        saleExitEntity.setNumber(String.valueOf(UUidUtils.uuid())); //销售退货单编号
        saleExitEntity.setExitNumber(saleList.getSaleNumber());  //销售单编号
        saleExitEntity.setNum(saleList.getNum());  //  数量
        saleExitEntity.setPrice(saleList.getPrice()); //价格
        saleExitEntity.setTotalPrice(saleList.getTotalPrice()); //总价
        saleExitEntity.setTime(LocalDateTime.now()); //时间
        saleExitEntity.setReason(remark); //原因
        if (saleList.getStatus() == 1) {  //        销售单进行中
            saleExitEntity.setStatus(0); //销售退货   完成
        } else {
            //销售单 完成情况
            //生成入库清单  通过查询出 出库清单 来生成 入库清单      ---------先完成出库功能
            /**
             * 判断是否存在出库清单
             */
            List<DepositoryOut> DepositoryOutList = depositoryOutService.getRowInfoByPurchaseNumber(Integer.parseInt(saleList.getSaleNumber()));
            DepositoryOut rowDepositoryOut = DepositoryOutList.get(0);
            if (rowDepositoryOut == null) {
                return 2;  //不存在对应的出库清单，回收异常
            }
            /**
             * 生成出库清单
             */
            DepositoryOut depositoryOut = new DepositoryOut();
            depositoryOut.setSourceNumber(Long.valueOf(saleList.getSaleNumber()));  //来源编号
            depositoryOut.setOutId(Long.valueOf(UUidUtils.uuid()));  //设置随机数 uuid
//            depositoryOut.setShopName(); //出库商品
            depositoryOutMapper.insert(depositoryOut); //  出库清单
            saleExitEntity.setStatus(1); //进行中
        }
        saleExitMapper.insert(saleExitEntity); //增加销售退货单
        return 0;  //操作成功
    }
}

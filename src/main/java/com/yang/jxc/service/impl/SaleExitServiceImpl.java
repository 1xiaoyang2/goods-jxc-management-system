package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.SaleExit;
import com.yang.jxc.mapper.SaleExitMapper;
import com.yang.jxc.service.SaleExitService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.UUidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门  service
 */
@Service
public class SaleExitServiceImpl implements SaleExitService {

    @Autowired
    private SaleExitMapper saleExitMapper;


    @Override
    public int create(SaleExit saleExit) {
        saleExit.setStatus(1); //默认进行中
        saleExit.setTime(LocalDateTime.now());
        saleExit.setExitNumber(String.valueOf(UUidUtils.uuid()));  //销售退货编号
        BigDecimal TotalPrice = null;  //不同属性的乘积问题
        BigDecimal price = saleExit.getPrice();
        Long num = saleExit.getNum();
        //        问题：  null  与  " "
        if (price != null && num != null) {
            BigDecimal bigDecimal = new BigDecimal(num);
            TotalPrice = bigDecimal.multiply(price);
            saleExit.setTotalPrice(TotalPrice);
        }
        return saleExitMapper.insert(saleExit);
    }

    @Override
    public int update(SaleExit saleExit) {
        //计算总价  直接重新计算一下
        Long num = saleExit.getNum(); //获取数量
        BigDecimal price = saleExit.getPrice();  //单价

        if (price != null && num != null) {
            BigDecimal numDivide = new BigDecimal(num);
            BigDecimal divideTotalPrice = numDivide.divide(price, 2);  //计算总价保留两位数
            saleExit.setTotalPrice(divideTotalPrice);
        }
        //updateByPrimaryKeySelective 传入字段为null则不更新
        return saleExitMapper.updateById(saleExit);
    }

    @Override
    public int delete(Long id) {
        return saleExitMapper.deleteById(id);
    }

    @Override
    public List<SaleExit> list() {
        return saleExitMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public CommonPage<SaleExit> list(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SaleExit> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), SaleExit::getNumber, keyword);
        IPage<SaleExit> page = new Page<>(pageNum, pageSize);
        IPage<SaleExit> saleExitIPage = saleExitMapper.selectPage(page, wrapper);
        return CommonPage.<SaleExit>builder().list(saleExitIPage.getRecords()).total(saleExitIPage.getTotal()).build();
    }

    @Override
    public int addOrUpdate(SaleExit saleExit) {
        // 判断 编号是否存在，如果存在就走更新的逻辑否则新增数据
        int res = 0;
        if (saleExit.getId() != null && saleExit.getId() != 0) {
            // 表示更新操作
            res = this.update(saleExit);
        } else {
            res = this.create(saleExit);
        }
        return res;
    }

}

package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.PurchaseExit;
import com.yang.jxc.mapper.PurchaseExitMapper;
import com.yang.jxc.service.PurchaseExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门  service
 */
@Service
public class PurchaseExitServiceImpl implements PurchaseExitService {

    @Autowired
    private PurchaseExitMapper purchaseExitMapper;


    @Override
    public int create(PurchaseExit purchaseExit) {
        purchaseExit.setTime(LocalDateTime.now());
        long TotalPrice = 120L;  //不同属性的乘积问题
        purchaseExit.setTotalPrice(BigDecimal.valueOf(TotalPrice));
        purchaseExit.setStatus(1); //这个设置int  修改varchar
        //设置编号
        purchaseExit.setExitNumber("123456"); //默认后期修改
        return purchaseExitMapper.insert(purchaseExit);
    }

    @Override
    public int update(PurchaseExit purchaseExit) {
        return purchaseExitMapper.updateById(purchaseExit);
    }

    @Override
    public int delete(Long id) {
        return purchaseExitMapper.deleteById(id);
    }

    @Override
    public List<PurchaseExit> list() {
        return purchaseExitMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public List<PurchaseExit> list(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<PurchaseExit> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(PurchaseExit::getNumber, keyword);
        IPage<PurchaseExit> page = new Page<>(pageNum, pageSize);
        return purchaseExitMapper.selectList(page, wrapper);
    }

    @Override
    public int addOrUpdate(PurchaseExit purchaseExit) {
        // 判断 编号是否存在，如果存在就走更新的逻辑否则新增数据
        int res = 0;
        if (purchaseExit.getId() != null && purchaseExit.getId() != 0) {
            // 表示更新操作
            res = this.update(purchaseExit);
        } else {
            res = this.create(purchaseExit);
        }
        return res;
    }

}

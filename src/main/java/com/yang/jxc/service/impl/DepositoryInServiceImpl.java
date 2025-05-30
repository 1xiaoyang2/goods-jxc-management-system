package com.yang.jxc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.yang.jxc.domain.entity.Customer;
import com.yang.jxc.domain.entity.DepositoryIn;
import com.yang.jxc.mapper.DepositoryInMapper;
import com.yang.jxc.service.DepositoryInService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.UUidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 入库清单
 */
@Service
public class DepositoryInServiceImpl implements DepositoryInService {

    @Autowired
    private DepositoryInMapper depositoryInMapper;

    @Override
    public int create(DepositoryIn DepositoryIn) {
        /*
        *         <!-- 后端计算总价 -->
        <!-- 入库人从后台token中获取-前端直接传的 -->
        <!-- 是否入库-可以不指定 数据库默认未入库---权限判断 -->
        <!-- 入库时间  -->
        *
        * */
        //uuid生成
        Integer uuid = UUidUtils.uuid();
        DepositoryIn.setInId(Long.valueOf(uuid));
        DepositoryIn.setStatus(1); //默认未入库
        DepositoryIn.setCreateDate(LocalDateTime.now());
        /**
         *  计算总价
         */
        BigDecimal shopPrice = DepositoryIn.getShopPrice();
        Long shopNumber = DepositoryIn.getShopNumber();
        if (shopPrice != null && shopNumber != null) {
            BigDecimal bigDecimal = new BigDecimal(shopNumber);
            BigDecimal priceTotal = bigDecimal.multiply(shopPrice);
            DepositoryIn.setPriceTotal(priceTotal);
        }
        return depositoryInMapper.insert(DepositoryIn);
    }

    @Override
    public int update(DepositoryIn DepositoryIn) {
        return depositoryInMapper.updateById(DepositoryIn);
    }

    @Override
    public int delete(Long id) {
        return depositoryInMapper.deleteById(id);
    }

    @Override
    public List<DepositoryIn> list() {
        return depositoryInMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public CommonPage<DepositoryIn> list(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<DepositoryIn> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), DepositoryIn::getShopName, keyword);
        IPage<DepositoryIn> page = new Page<>(pageNum, pageSize);
        IPage<DepositoryIn> depositoryInIPage = depositoryInMapper.selectPage(page, wrapper);
        return CommonPage.<DepositoryIn>builder().list(depositoryInIPage.getRecords()).total(depositoryInIPage.getTotal()).build();
    }

    @Override
    public boolean checkDepositoryInId(Long depositoryInId) {
        LambdaQueryWrapper<DepositoryIn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepositoryIn::getId, depositoryInId);
        DepositoryIn depositoryIn = depositoryInMapper.selectOne(wrapper);
        return depositoryIn.getStatus() == 0;
    }

    @Override
    public int addOrUpdateDepositoryIn(DepositoryIn depositoryIn) {
        // 判断 编号是否存在，如果存在就走更新的逻辑否则新增数据
        int res = 0;
        if (depositoryIn.getId() != null && depositoryIn.getId() != 0) {
            // 表示更新操作
            res = this.update(depositoryIn);
        } else {
            res = this.create(depositoryIn);
        }
        return res;
    }


    /**
     * 质检 =入库
     *
     * @param id
     * @return
     */
    @Override
    public int checkById(Long id) {
        //校验id
        if (id == null) return 0;
        //更新的内容
        DepositoryIn updateDepositoryIn = new DepositoryIn();
        updateDepositoryIn.setIsInspection(0); //质检 0为已质检
        DepositoryIn selectDepositoryIn = depositoryInMapper.selectById(id);  //通过id获取数据
        //入库商品 -入库仓库  |   入库仓库 - 入库的数量

        if (selectDepositoryIn.getIsInspection() == 1) {  //   判断是否质检状态  1未质检  0 质检
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String userName = token.getPrincipal().toString();
            updateDepositoryIn.setInUser(userName);   //设置入库人
            updateDepositoryIn.setDate(LocalDateTime.now());  //设置入库日期
            updateDepositoryIn.setStatus(0); //设置 入库0
        }

        /**
         * 入库后需要更新库存  1.库存列表中库存数量
         *              2.仓库列表中的 仓库剩余量
         */
        return depositoryInMapper.updateById(updateDepositoryIn);
    }

    /**
     * 关联采购表    采购编号
     * 通过 采购编号 获取入库清单
     *
     * @param number
     * @return
     */
    @Override
    public List<DepositoryIn> getRowInfoByPurchaseNumber(int number) {
        LambdaQueryWrapper<DepositoryIn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepositoryIn::getSourceNumber, number);
        return depositoryInMapper.selectList(wrapper);
    }
}

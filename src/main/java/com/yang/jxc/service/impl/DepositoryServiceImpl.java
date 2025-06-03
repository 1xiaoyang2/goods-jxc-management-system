package com.yang.jxc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.yang.jxc.domain.dto.StockUpDTO;
import com.yang.jxc.domain.entity.Depository;
import com.yang.jxc.domain.entity.Note;
import com.yang.jxc.mapper.DepositoryMapper;
import com.yang.jxc.service.DepositoryService;
import com.yang.jxc.utils.CommonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepositoryServiceImpl implements DepositoryService {

    @Autowired
    private DepositoryMapper depositoryMapper;


    @Override
    public int create(Depository depository) {
        depository.setBuildDate(LocalDateTime.now());
        depository.setStatus(0);
        depository.setArea("立方米");
        depository.setSurplus(depository.getStockTotal());
        return depositoryMapper.insert(depository);
    }

    @Override
    public int update(Depository depository) {
        //仓库名称更新后，也需要同步更新入库单和出库单 商品库存中的仓库名称？
        //如果提交的参数和数据库中的 仓库名称数据一样则直接更新
        //否则 同步更新 入库单和出库单 商品库存中的仓库名称
        return depositoryMapper.updateById(depository);
    }

    @Override
    public int delete(Long id) {
        return depositoryMapper.deleteById(id);
    }

    @Override
    public List<Depository> list() {
        return depositoryMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public CommonPage<Depository> list(String keyword, Integer pageSize, Integer pageNum) {
        if(keyword != null){
            keyword = keyword.trim();
        }
        LambdaQueryWrapper<Depository> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Depository::getName, keyword);
        IPage<Depository> page = new Page<>(pageNum, pageSize);
        IPage<Depository> depositoryIPage = depositoryMapper.selectPage(page, wrapper);
        return CommonPage.<Depository>builder().list(depositoryIPage.getRecords()).total(depositoryIPage.getTotal()).build();
    }

    @Override
    public boolean checkUserName(String name) {
        LambdaQueryWrapper<Depository> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Depository::getName, name);
        return !CollectionUtils.isEmpty(depositoryMapper.selectList(wrapper));
    }

    @Override
    public int updateOrAddById(Depository depository) {
        if (depository.getId() != null && depository.getId() != 0) { //更新
            this.update(depository);
        } else {
            this.create(depository);
        }
        return 1;  //默认成功
    }

    /**
     * 更新 某仓库剩余量
     *
     * @param stockUpDto@return
     */
    @Override
    public int updateAreaByTwoName(StockUpDTO stockUpDto) {
        LambdaUpdateWrapper<Depository> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Depository::getName, stockUpDto.getDepositoryName());
        wrapper.setSql("surplus = surplus" + (stockUpDto.getFlag() == 0 ? "+" : "-") + stockUpDto.getQuantity());
        return depositoryMapper.update(wrapper);
    }


    /**
     * name：  value
     * 例；  北京市/  需要截取/之前的  ；   :值 设置？ 固定 70
     *
     * @return
     */
    @Override
    public List<Map<String, String>> getDepositoryToEChart() {
        List<Map<String, String>> list = new ArrayList<>();
        List<Depository> depositoryList = depositoryMapper.selectList(new LambdaQueryWrapper<>());
        if (depositoryList != null) {
            for (Depository depository : depositoryList) {
                String address = depository.getAddress();
                String subAddress = address.substring(0, address.indexOf("/"));
                //编 value怎么计算：暂时统一设置为70

                if (!subAddress.isEmpty()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", subAddress);
                    map.put("value", String.valueOf(70));
                    list.add(map);
                }
            }
        }
        return list;
    }


    /**
     * 获取仓库的数量
     *
     * @return
     */
    @Override
    public int getLength() {
        return Math.toIntExact(depositoryMapper.selectCount(new LambdaQueryWrapper<>()));
    }
}

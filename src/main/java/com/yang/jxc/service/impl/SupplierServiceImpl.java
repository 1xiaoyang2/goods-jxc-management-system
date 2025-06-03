package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.Supplier;
import com.yang.jxc.mapper.SupplierMapper;
import com.yang.jxc.service.SupplierService;
import com.yang.jxc.utils.CommonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门  service
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public int updateOrAddById(Supplier supplier) {
        if (supplier.getId() != null && supplier.getId() != 0) {//更新
            this.update(supplier);
        } else {
            this.create(supplier);
        }
        //先默认删除/修改 成功
        return 1;
    }

    public int create(Supplier supplier) {
        supplier.setCreateDate(LocalDateTime.now());
        // dept.setAdminCount(0);
        //  role.setSort(0);
        return supplierMapper.insert(supplier);
    }

    public int update(Supplier supplier) {
        supplier.setUpdateTime(LocalDateTime.now());
        return supplierMapper.updateById(supplier);
    }

    @Override
    public int delete(Long id) {
        return supplierMapper.deleteById(id);
    }

    @Override
    public List<Supplier> list() {
        return supplierMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public CommonPage<Supplier> list(String keyword, Integer pageNum, Integer pageSize) {
        if(keyword != null){
            keyword = keyword.trim();
        }
        LambdaQueryWrapper<Supplier> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Supplier::getSupplierName, keyword);
        IPage<Supplier> page = new Page<>(pageNum, pageSize);
        IPage<Supplier> supplierIPage = supplierMapper.selectPage(page, wrapper);
        return CommonPage.<Supplier>builder().list(supplierIPage.getRecords()).total(supplierIPage.getTotal()).build();
    }

    @Override
    public List<Map<String, String>> getNameAndAddress() {
        List<Map<String, String>> list = new ArrayList<>();
        List<Supplier> supplierList = supplierMapper.selectList(new LambdaQueryWrapper<>());
        if (supplierList != null) {
            for (Supplier supplier : supplierList) {
                String supplierName = supplier.getSupplierName();
                String address = supplier.getAddress();
                Map<String, String> map = new HashMap<>();
                map.put("value", supplierName);
                map.put("address", address);
                list.add(map);
            }
        }
        return list;
    }
}

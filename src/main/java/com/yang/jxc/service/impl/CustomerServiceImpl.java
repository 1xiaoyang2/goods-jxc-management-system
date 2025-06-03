package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.Customer;
import com.yang.jxc.mapper.CustomerMapper;
import com.yang.jxc.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public int createOrUpdate(Customer customer) {
        Customer selectCustomer = customerMapper.selectById(customer.getId());
        customer.setUpdateTime(LocalDateTime.now());
        if (selectCustomer != null) {
            return customerMapper.updateById(customer);
        }else {
            return customerMapper.insert(customer);
        }
    }

    @Override
    public int delete(long id) {
        return customerMapper.deleteById(id);
    }

    @Override
    public CommonPage<Customer> list(String keyword, Integer pageNum, Integer pageSize) {
        if(keyword != null){
            keyword = keyword.trim();
        }
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Customer::getName, keyword);
        IPage<Customer> page = new Page<>(pageNum, pageSize);
        IPage<Customer> customerIPage = customerMapper.selectPage(page, wrapper);
        return CommonPage.<Customer>builder().list(customerIPage.getRecords()).total(customerIPage.getTotal()).build();
    }

    /**
     * 获取 id 和 客户名
     *
     * @return
     */
    @Override
    public List<Map<String, String>> getIDAndCustomerName() {
        List<Map<String, String>> list = new ArrayList<>();
        List<Customer> customerList = customerMapper.selectList(new LambdaQueryWrapper<>());
        if (customerList != null) {
            for (Customer customer : customerList) {
                Long id = customer.getId();
                String customerName = customer.getName();
                //编号不能为空
                if (id != null && customerName != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", customerName);
                    map.put("id", String.valueOf(id));
                    list.add(map);
                }
            }
        }
        return list;
    }
}

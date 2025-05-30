package com.yang.jxc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.yang.jxc.domain.entity.Customer;
import com.yang.jxc.domain.entity.Dept;
import com.yang.jxc.mapper.CustomerMapper;
import com.yang.jxc.service.CustomerService;
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
    public int create(Customer customer) {
        customer.setBuildDate(LocalDateTime.now());
        return customerMapper.insert(customer);
    }

    @Override
    public int update(Customer customer) {
        return customerMapper.updateById(customer);
    }

    @Override
    public int delete(long id) {
        return customerMapper.deleteById(id);
    }

    @Override
    public List<Customer> list() {
        return customerMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public List<Customer> list(String keyword, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Customer::getName, keyword);
        IPage<Customer> page = new Page<>(pageNum, pageSize);
        return customerMapper.selectList(page, wrapper);
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

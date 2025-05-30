package com.yang.jxc.controller;


import com.yang.jxc.domain.entity.Customer;
import com.yang.jxc.service.CustomerService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "CustomerController", description = "基础信息管理-客户表")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("添加客户")
    @PostMapping("/add")
    public CommonResult<Integer> create(@RequestBody Customer customer) {
        int count = customerService.create(customer);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改客户")
    @PostMapping("/update")
    public CommonResult<Integer> update(@RequestBody Customer customer) {
        int count = customerService.update(customer);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    @ApiOperation("删除客户")
    @PostMapping(value = "/delete")
    public CommonResult<String> delete(Long id) {
        int count = customerService.delete(id);
        if (count > 0) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取所有客户")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Customer>> listAll() {
        List<Customer> customerList = customerService.list();

        return CommonResult.success(customerList);
    }

    @ApiOperation("根据客户姓名或 地区获取客户")
    @GetMapping(value = "/listByName")
    public CommonResult<CommonPage<Customer>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<Customer> customerList = customerService.list(keyword, pageNum, pageSize);
        System.out.println("前端传递的分页内容：" + "查询参数：" + keyword + "\t" + "当前页码：" + pageNum + "\t" + "每页条数" + pageSize);
        return CommonResult.success(CommonPage.restPage(customerList));
    }

    /**
     * 获取 客户id和 客户
     *
     * @return
     */
    @ApiOperation("获取销售编号和销售客户")
    @GetMapping(value = "/getIDAndCustomerName")
    public CommonResult<List<Map<String, String>>> getNumberAndCustomer() {
        List<Map<String, String>> list = customerService.getIDAndCustomerName();
        if (list != null) {
            return CommonResult.success(list);
        }
        return CommonResult.failed("数据为空");
    }
}

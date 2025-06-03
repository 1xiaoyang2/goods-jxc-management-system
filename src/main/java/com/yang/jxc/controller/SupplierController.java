package com.yang.jxc.controller;


import com.yang.jxc.domain.entity.Supplier;
import com.yang.jxc.service.SupplierService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "SupplierController", description = "基础信息管理-供应商表")
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @ApiOperation("添加供应商")
    @PostMapping(value = "/addOrUpdate")
    public CommonResult<Integer> create(@RequestBody Supplier supplier) {
        int count = supplierService.updateOrAddById(supplier);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除客户")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(Long id) {
        int count = supplierService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    @ApiOperation("根据 供应商姓名或 地区分页获取 ")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<Supplier>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(supplierService.list(keyword, pageNum, pageSize));
    }


    // TODO encheck
    @ApiOperation("获取供应商名和地址")
    @GetMapping(value = "/getNameAndAddress")
    public CommonResult<List<Map<String, String>>> getNameAndAddress() {
        List<Map<String, String>> list = supplierService.getNameAndAddress();
        if (list != null) {
            return CommonResult.success(list);
        }
        return CommonResult.failed("数据为空");
    }
}

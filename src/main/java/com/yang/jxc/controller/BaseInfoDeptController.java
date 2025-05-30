package com.yang.jxc.controller;


import com.yang.jxc.domain.entity.Dept;
import com.yang.jxc.service.DeptService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 基础信息管理模块 -部门资料是调用系统管理中的部门，且只有查询 操作-------
 * 也可以不需要这个controller 使用系统管理的部门
 */

@RestController
@Api(tags = "BaseInfoDeptController", description = "基础信息管理-部门")
@RequestMapping("/jcDept")
public class BaseInfoDeptController {

    @Autowired
    private DeptService deptService;


    @ApiOperation("获取所有部门")
    @GetMapping("/listAll")
    public CommonResult<List<Dept>> listAll() {
        List<Dept> customerList = deptService.list();
        return CommonResult.success(customerList);
    }

    @ApiOperation("根据部门名或 地区获取部门")
    @GetMapping("/list")
    public CommonResult<CommonPage<Dept>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Dept> customerList = deptService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(customerList));
    }
}

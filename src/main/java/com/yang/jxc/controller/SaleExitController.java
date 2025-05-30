package com.yang.jxc.controller;

import com.yang.jxc.domain.entity.SaleExit;
import com.yang.jxc.service.SaleExitService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "SaleExitController", description = "进销管理-销售退货表")
@RequestMapping("/saleExit")
public class SaleExitController {

    @Autowired
    private SaleExitService saleExitService;


    @ApiOperation("添加 ")
    @PostMapping(value = "/add")
    public CommonResult<Integer> create(@RequestBody SaleExit saleExit) {
        int count = saleExitService.addOrUpdate(saleExit);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
//
//    @ApiOperation("修改 ")
//    @RequestMapping(value = "/update")
//    @ResponseBody
//    public CommonResult update( @RequestBody  SaleExit saleExit) {
//        int count = saleExitService.update(saleExit);
//        if (count > 0) {
//            return CommonResult.success(count);
//        }
//        return CommonResult.failed();
//    }

    @ApiOperation("批量删除 ")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(Long id) {
        int count = saleExitService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取所有 ")
    @GetMapping(value = "/listAll")
    public CommonResult<List<SaleExit>> listAll() {
        List<SaleExit> saleExit = saleExitService.list();
        return CommonResult.success(saleExit);
    }

    @ApiOperation("根据编号获取")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<SaleExit>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SaleExit> saleList = saleExitService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(saleList));
    }
}

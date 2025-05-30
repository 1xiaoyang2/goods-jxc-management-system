package com.yang.jxc.controller;

import com.yang.jxc.domain.entity.PurchaseExit;
import com.yang.jxc.service.PurchaseExitService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "PurchaseExitController", description = "进销管理-采购退货表")
@RequestMapping("/purchaseExit")
public class PurchaseExitController {

    @Autowired
    private PurchaseExitService purchaseExitService;

    @ApiOperation("添加记录")
    @PostMapping(value = "/add")
    public CommonResult<Integer> create(@RequestBody PurchaseExit purchaseExit) {
        int count = purchaseExitService.addOrUpdate(purchaseExit);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改记录")
    @PostMapping(value = "/update")
    public CommonResult<Integer> update(@RequestBody PurchaseExit purchaseExit) {
        int count = purchaseExitService.update(purchaseExit);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除采购退货单")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(Long id) {
        int count = purchaseExitService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

//    @ApiOperation("获取所有 ")
//    @RequestMapping(value = "/listAll")
//    @ResponseBody
//    public CommonResult<List<Purchase>> listAll() {
//        List<Purchase> customerList = purchaseService.list();
//        return CommonResult.success(customerList);
//    }

    @ApiOperation("根据退货编号-分页获取 ")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<PurchaseExit>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(purchaseExitService.list(keyword, pageSize, pageNum));
    }
}

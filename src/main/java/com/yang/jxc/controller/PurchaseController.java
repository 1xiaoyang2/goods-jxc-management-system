package com.yang.jxc.controller;


import com.yang.jxc.domain.entity.Purchase;
import com.yang.jxc.service.PurchaseService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import com.yang.jxc.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(tags = "PurchaseController", description = "进销管理-采购表")
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @ApiOperation("添加采购单")
    @PostMapping(value = "/add")
    public CommonResult<Integer> create(@RequestBody Purchase purchase) {
        int count = purchaseService.addOrUpdate(purchase);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.success(count);
    }

    @ApiOperation("修改采购-合并到add")
    @PostMapping(value = "/update")
    public CommonResult<Integer> update(@RequestBody Purchase purchase) {
        int count = purchaseService.update(purchase);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation(" 删除采购")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(Long id) {
        int count = purchaseService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取所有采购表")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Purchase>> listAll() {
        List<Purchase> customerList = purchaseService.list();
        return CommonResult.success(customerList);
    }

    @ApiOperation("根据采购人 获取 ")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<Purchase>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Purchase> customerList = purchaseService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(customerList));
    }

    /**
     * 入库操作
     * 前端传到后端的参数为 null问题
     *
     * @param
     * @return
     */
    @ApiOperation("采购单完成后 入库")
    @PostMapping(value = "/putStock")
    public CommonResult<ResultCode> putStock(@RequestParam(value = "depositoryName", required = false) String depositoryName,
                                 @RequestBody Purchase purchaseList) {

        int count = purchaseService.putStock(depositoryName, purchaseList);
        if (count == 0) {
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        if (count == 2) {
            return CommonResult.failed(ResultCode.LACK_STOCK);
        }
        if (count == 3) {
            return CommonResult.failed(ResultCode.REPEAT);
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @ApiOperation("获取采购编号和采购供应商")
    @GetMapping(value = "/getNumberAndSupplierName")
    public CommonResult<List<Map<String, String>>> getNumberAndSupplierName() {
        List<Map<String, String>> list = purchaseService.getNumberAndSupplierName();
        if (list != null) {
            return CommonResult.success(list);
        }
        return CommonResult.failed("数据为空");
    }

    /**
     * status 是0 完成 则需要生成出库清单
     * 是1 则直接更新状态为0 和备注
     *
     * @return
     */
    @ApiOperation("校验后----退货")
    @PostMapping(value = "/checkAndExitGoods")
    public CommonResult<Object> checkAndExitGoods(@RequestParam(value = "remark") String remark,
                                                   @RequestBody Purchase purchase) {
        purchase.setRemark(remark);
        int count = purchaseService.checkAndExitGoods(purchase);
        if (count == 0) {  //已退货
            return CommonResult.success(ResultCode.OVER_EXIT_GOODS.getCode(), ResultCode.OVER_EXIT_GOODS.getMessage());
        }
        //有问题
        return CommonResult.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }
}

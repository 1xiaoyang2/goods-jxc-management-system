package com.yang.jxc.controller;

import com.yang.jxc.domain.entity.Sale;
import com.yang.jxc.service.SaleService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import com.yang.jxc.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "SaleController", description = "进销管理-销售表")
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;


    @ApiOperation("添加 ")
    @PostMapping(value = "/add")
    public CommonResult<Integer> create(@RequestBody Sale sale) {
        int count = saleService.addOrUpdate(sale);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改 ")
    @PostMapping(value = "/update")
    public CommonResult<Integer> update(@RequestBody Sale sale) {
        int count = saleService.update(sale);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除 ")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(Long id) {
        int count = saleService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取所有 ")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Sale>> listAll() {
        List<Sale> customerList = saleService.list();
        return CommonResult.success(customerList);
    }

    @ApiOperation("根据编号获取")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<Sale>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Sale> saleList = saleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(saleList));
    }

    @ApiOperation("获取销售编号和销售客户")
    @GetMapping(value = "/getNumberAndCustomer")
    public CommonResult<List<Map<String, String>>> getNumberAndCustomer() {
        List<Map<String, String>> list = saleService.getNumberAndCustomer();
        if (list != null) {
            return CommonResult.success(list);
        }
        return CommonResult.failed("数据为空");
    }

    /**
     * @param depositoryName 仓库
     * @param saleList       销售订单
     * @return
     */
    @ApiOperation("销售-  出库")
    @PostMapping(value = "/outSale")
    public CommonResult<ResultCode> outSale(@RequestParam(value = "depositoryName", required = false) String depositoryName,
                                @RequestBody Sale saleList) {
        Map<String, Object> map = new HashMap<>();
        map.put("depositoryName", depositoryName);
        map.put("saleList", saleList);
        int result = saleService.outSaleAndCheck(map);
        /**
         *  定义接口
         *  0：成功
         *  失败 ！=服务器错误
         *  1： 库存不足，请重新选择出库仓库
         *  2： 不能重复出库
         */
        if (result == 1) {
            return CommonResult.failed(ResultCode.LACK_STOCK);
        } else if (result == 2) {
            return CommonResult.failed(ResultCode.OVER_EXIT_SALE);
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 回收
     *
     * @param remark   回收原因
     * @param saleList 销售列表
     * @return
     */
    @ApiOperation("回收")
    @PostMapping(value = "/checkInGoods")
    public CommonResult<ResultCode> checkInGoods(@RequestParam(value = "remark", required = false) String remark,
                                     @RequestBody Sale saleList) {
        Map<String, Object> map = new HashMap<>();
        map.put("remark", remark);
        map.put("saleList", saleList);
        int result = saleService.checkInGoods(map);
        /**
         * 0 操作成功
         * 失败
         * 1 ：已生成销售退货单
         * 2: 原出库清单不存在，回收异常
         */
        if (result == 1) {
            return CommonResult.failed(ResultCode.CREATE_S);
        } else if (result == 2) {
            return CommonResult.failed(ResultCode.NOT_EXIT);
        }
        return CommonResult.success(ResultCode.SUCCESS);
    }
}

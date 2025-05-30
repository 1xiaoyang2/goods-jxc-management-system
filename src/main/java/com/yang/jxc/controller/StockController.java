package com.yang.jxc.controller;

import com.yang.jxc.domain.entity.Stock;
import com.yang.jxc.service.StockService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品名称  存入仓库 来判断是否是新的记录行，还是在同一个商品上更新数量
 */

@RestController
@Api(tags = "StockController", description = "系统管理-商品库存")
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;


    @ApiOperation("添加库存")
    @PostMapping(value = "/add")
    public CommonResult<String> create(@RequestBody Stock stock) {
        int count = stockService.addOrUpdateStock(stock);
        if (count > 0) {
            return CommonResult.success("成功");
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改库存-整合到add")
    @PostMapping(value = "/update/{id}")
    public CommonResult<Integer> update(@RequestBody Stock stock) {
        int count = stockService.update(stock);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除库存")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(Long id) {
        int count = stockService.delete(id);
        return CommonResult.success(count);
    }

    @ApiOperation("获取所有库存")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Stock>> listAll() {
        List<Stock> stockList = stockService.list();
        return CommonResult.success(stockList);
    }

    @ApiOperation("根据商品或库存数量获取库存")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<Stock>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(stockService.list(keyword, pageSize, pageNum));
    }

    /**
     * 大屏数据 之  柱状图
     */
    @ApiOperation("柱状图-库存列表-仓库集合的")
    @GetMapping(value = "/histogramList")
    public CommonResult<List<Map<String, List<?>>>> histogramList() {
        List<Map<String, List<?>>> histogramList = stockService.histogramList();

        return CommonResult.success(histogramList);
    }


    /**
     * **************大屏数据 之  饼图 Pie
     *
     * @return
     */
    @ApiOperation("饼图-库存列表-仓库集合的")
    @GetMapping(value = "/Pie")
    public CommonResult<List> Pie() {
        List shopMap = stockService.shopMap();
        return CommonResult.success(shopMap);
    }
}

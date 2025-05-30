package com.yang.jxc.controller;


import com.yang.jxc.domain.entity.Shop;
import com.yang.jxc.domain.entity.ShopType;
import com.yang.jxc.service.ShopService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "ShopCustomer", description = "基础信息管理-商品")
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;


    @ApiOperation("添加商品")
    @PostMapping(value = "/add")
    public CommonResult<Integer> create(@RequestBody Shop shop) {
        int count = shopService.updateOrAddById(shop);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    //---------------
    @ApiOperation("修改商品")
    @PostMapping(value = "/update/{id}")
    public CommonResult<Integer> update(@PathVariable Long id, @RequestBody Shop shop) {
        int count = shopService.update(shop);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除商品")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(Long id) {
        int count = shopService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

//    @ApiOperation("获取所有商品")
//    @RequestMapping(value = "/listAll")
//    @ResponseBody
//    public CommonResult<List<Shop>> listAll() {
//        List<Shop> customerList = shopService.list();
//        return CommonResult.success(customerList);
//    }

    @ApiOperation("根据商品名或 地区获取商品")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<Shop>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(shopService.list(keyword, pageNum, pageSize));
    }

    //获取 getShopNameAll  id和 商品名称
    @ApiOperation("获取所有的商品id和名称")
    @GetMapping(value = "/getShopNameAll")
    public CommonResult<ArrayList<Map<String, Object>>> getShopNameAll() {
        ArrayList<Map<String, Object>> shopNameAll = shopService.getShopNameAll();
        return CommonResult.success(shopNameAll);
    }

    @ApiOperation("根据商品名称获取商品类型")
    @GetMapping(value = "/getShopTypeByName")
    public CommonResult<ShopType> selectShopTypeByName(String shopName) {
        ShopType shopType = shopService.selectShopTypeByName(shopName);

        return CommonResult.success(shopType);
    }

    //获取商品类型
    @ApiOperation("获取商品类型列表")
    @GetMapping(value = "/getShopTypeList")
    public CommonResult<List<ShopType>> selectShopTypeList() {
        List<ShopType> shopTypeList = shopService.selectShopTypeList();
        return CommonResult.success(shopTypeList);
    }
}

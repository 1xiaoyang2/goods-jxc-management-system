package com.yang.jxc.controller;


import com.yang.jxc.domain.entity.Depository;
import com.yang.jxc.service.DepositoryService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "DepositoryController", description = "仓库管理-仓库列表")
@RequestMapping("/depository")
public class DepositoryController {

    @Autowired
    private DepositoryService depositoryService;

    @ApiOperation("添加仓库")
    @PostMapping("/add")
    public CommonResult<String> create(@RequestBody Depository depository) {
        System.out.println("添加：" + depository.getId());
        int count = depositoryService.updateOrAddById(depository);
        if (count > 0) {
            return CommonResult.success("成功");
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改仓库-归并到add")
    @PostMapping("/update")
    public CommonResult<Integer> update(@RequestBody Depository depository) {
        int count = depositoryService.update(depository);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除仓库")
    @PostMapping("/delete")
    public CommonResult<String> delete(Long id) {
        int count = depositoryService.delete(id);
        if (count > 0) {
            return CommonResult.success("成功");
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取所有仓库")
    @GetMapping("/listAll")
    public CommonResult<List<Depository>> listAll() {
        List<Depository> depositoryList = depositoryService.list();
        return CommonResult.success(depositoryList);
    }

    @ApiOperation("根据角色名称分页获取仓库列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<Depository>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(depositoryService.list(keyword, pageSize, pageNum));
    }

    @ApiOperation(value = "仓库是否存在", notes = "校验仓库名称")
    @GetMapping("/checkDepositoryName")
    public CommonResult<String> checkDepositoryName(String depositoryName) {
        boolean result = depositoryService.checkUserName(depositoryName);
        if (result) {
            return CommonResult.success("YES");
        } else {
            return CommonResult.success("NO");
        }
    }

    @ApiOperation(value = "大屏-全国仓库地图", notes = "地图")
    @GetMapping("/getDepositoryToEChart")
    public CommonResult<List<Map<String, String>>> getDepositoryToEchart() {
        List<Map<String, String>> list = depositoryService.getDepositoryToEChart();
        if (list != null) {
            return CommonResult.success(list);
        }
        return CommonResult.failed("数据为空");
    }

    @ApiOperation(value = "获取仓库数量", notes = "仓库数量")
    @GetMapping("/getLength")
    public CommonResult<Integer> getLengthToDepository() {
        int count = depositoryService.getLength();
        System.out.println("仓库数量" + count);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}

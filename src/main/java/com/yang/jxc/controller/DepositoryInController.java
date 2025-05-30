package com.yang.jxc.controller;

import com.yang.jxc.domain.entity.DepositoryIn;
import com.yang.jxc.service.DepositoryInService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "DepositoryInController", description = "仓库管理-3入库清单")
@RequestMapping("/depositoryIn")
public class DepositoryInController {

    @Autowired
    private DepositoryInService depositoryInService;


    @ApiOperation("添加入库单")
    @PostMapping(value = "/add")
    public CommonResult<Integer> create(@RequestBody DepositoryIn depositoryIn) {
        int count = depositoryInService.addOrUpdateDepositoryIn(depositoryIn);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改入库单")
    @PostMapping(value = "/update")
    public CommonResult<Integer> update(@RequestBody DepositoryIn depositoryIn) {
        int count = depositoryInService.update(depositoryIn);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除入库单")
    @PostMapping(value = "/delete")
    public CommonResult<String> delete(Long id) {
        int count = depositoryInService.delete(id);
        if (count > 0) {
            return CommonResult.success("成功");
        }
        return CommonResult.failed();
    }


    @ApiOperation("根据仓库名称分页获取入库单信息")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<DepositoryIn>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<DepositoryIn> menuList = depositoryInService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(menuList));
    }


    @ApiOperation(value = "检验--是否已入库-入库编号", notes = "校验是否已入库")
    @GetMapping("/checkDepositoryInId")
    public CommonResult<String> checkRoleName(Long DepositoryInId) {
        boolean result = depositoryInService.checkDepositoryInId(DepositoryInId);
        if (result) {
            return CommonResult.success("YES");
        } else {
            return CommonResult.success("NO");
        }
    }

    /**
     * 商品质检后入库
     */
    @ApiOperation(value = "入库清单-质检")
    @PostMapping("/checkById")
    public CommonResult<Integer> checkById(@RequestParam(value = "id") Long id) {
        int count = depositoryInService.checkById(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}

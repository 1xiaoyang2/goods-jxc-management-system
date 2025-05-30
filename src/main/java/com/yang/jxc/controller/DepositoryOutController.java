package com.yang.jxc.controller;

import com.yang.jxc.domain.entity.DepositoryOut;
import com.yang.jxc.service.DepositoryOutService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(value = "OutDepositoryController", description = "系统管理-出库清单")
@RequestMapping("/depositoryOut")
public class DepositoryOutController {

    @Autowired
    private DepositoryOutService depositoryOutService;


    @ApiOperation("添加仓库")
    @PostMapping(value = "/add")
    public CommonResult<String> create(@RequestBody DepositoryOut DepositoryOut) {
        int count = depositoryOutService.addOrUpdateDepositoryOut(DepositoryOut);
        if (count > 0) {
            return CommonResult.success("成功");
        }
        return CommonResult.failed();
    }

//    @ApiOperation("修改仓库-add合并")
//    @RequestMapping(value = "/update/")
//    @ResponseBody
//    public CommonResult update(  @RequestBody DepositoryOut DepositoryOut) {
//        int count = depositoryOutService.update( DepositoryOut);
//        if (count > 0) {
//            return CommonResult.success(count);
//        }
//        return CommonResult.failed();
//    }

    @ApiOperation("删除仓库清单")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(Long id) {
        int count = depositoryOutService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取所有仓库")
    @GetMapping(value = "/listAll")
    public CommonResult<List<DepositoryOut>> listAll() {
        List<DepositoryOut> outList = depositoryOutService.list();
        return CommonResult.success(outList);
    }

    @ApiOperation("根据角色名称分页获取仓库列表")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<DepositoryOut>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<DepositoryOut> outList = depositoryOutService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(outList));
    }

    @ApiOperation("修改仓库状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult<Integer> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer OutInspection) {
        DepositoryOut depositoryOut = new DepositoryOut();
        depositoryOut.setOutInspection(OutInspection);
        int count = depositoryOutService.update(depositoryOut);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 商品 审核后出库
     * 1. 判断是否重复出库，否执行2
     * 2. 出库后增加仓库容量， 及  减少库存容量
     */
    @ApiOperation(value = "出库清单-审核")
    @PostMapping(value = "/checkById")
    public CommonResult<Integer> checkById(@RequestParam(value = "id") Long id) {
        int count = depositoryOutService.checkById(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("库存不足");
    }
}

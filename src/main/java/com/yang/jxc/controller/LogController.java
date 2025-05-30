package com.yang.jxc.controller;

import com.yang.jxc.domain.entity.Log;
import com.yang.jxc.service.LogService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author :ygy
 * @description :
 * @create :2023-07-20 11:23:00
 */

@RestController
@Api("日志")
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @ApiOperation("分页获取日志")
    @GetMapping("/list")
    public CommonResult<CommonPage<Log>> listByName(@RequestParam(value = "keyword", required = false) String keyword,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(logService.getList(keyword, pageSize, pageNum));
    }
}

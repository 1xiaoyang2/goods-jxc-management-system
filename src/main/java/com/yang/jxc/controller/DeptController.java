package com.yang.jxc.controller;


import com.yang.jxc.domain.entity.Dept;
import com.yang.jxc.service.DeptService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "DeptController", description = "系统管理-部门")
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;


    @ApiOperation("添加部门")
    @PostMapping(value = "/add")
    public CommonResult<Object> create(@RequestBody Dept dept) {
        int count = deptService.updateOrAddById(dept);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    @ApiOperation(value = "部门是否存在", notes = "校验部门名称")
    @GetMapping(value = "/checkdeptName")
    public CommonResult<String> checkDeptName(@RequestParam(value = "deptName") String deptName) {
        boolean result = deptService.checkDeptName(deptName);
        if (result) {
            return CommonResult.success("YES");
        } else {
            return CommonResult.success("NO");
        }
    }

    @ApiOperation("删除部门")
    @PostMapping(value = "/delete")
    public CommonResult<Object> delete(Long id) {
        int count = deptService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    @ApiOperation("根据部门名称分页获取部门列表")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<Dept>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(deptService.list(keyword, pageSize, pageNum));
    }

    @ApiOperation("修改部门状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult<Object> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        Dept XtDept = new Dept();
        XtDept.setStatus(status);
        int count = deptService.update(XtDept);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}

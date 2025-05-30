package com.yang.jxc.controller;


import com.yang.jxc.domain.dto.AdminRoleRelationDTO;
import com.yang.jxc.domain.dto.UpdateAdminPasswordParam;
import com.yang.jxc.domain.entity.Admin;
import com.yang.jxc.service.AdminService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 后台用户管理Controller
 */
@RestController
@Api(tags = "AdminController", description = "系统管理-用户")
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "通过token中用户名-获取用户信息")
    @GetMapping("/getAdminInfo")
    public CommonResult<List<Admin>> getAdminInfo() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) token.getPrincipal();
        List<Admin> infoList = adminService.getAdminInfo(userName);
        return CommonResult.success(infoList);
    }


    @ApiOperation(value = "用户是否存在", notes = "校验用户名称")
    @GetMapping("/checkUserName")
    public CommonResult<String> checkRoleName(@RequestParam(value = "userName") String userName) {
        boolean result = adminService.checkUserName(userName);
        if (result) {
            return CommonResult.success("YES");
        } else {
            return CommonResult.success("NO");
        }
    }


    @ApiOperation(value = "根据用户名或姓名--分页获取用户列表")
    @GetMapping("/List")
    public CommonResult<CommonPage<Admin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(adminService.getAdminList(keyword, pageSize, pageNum));
    }


    @ApiOperation(value = "添加用户")
    @PostMapping("/add")
    public CommonResult<String> create(@RequestBody Admin admin) {
        int count = adminService.updateOrAddById(admin);
        if (count > 0) {
            return CommonResult.success("成功");
        }
        return CommonResult.failed();
    }


    @ApiOperation(value = "修改用户信息")
    @PostMapping("/update")
    public CommonResult<Integer> update(Admin admin) {
        int count = adminService.update(admin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改密码，密码校验")
    @PostMapping("/checkPassword")
    public CommonResult<String> checkPassword(@RequestBody UpdateAdminPasswordParam updateCheck) {
        boolean flag = adminService.checkPassword(updateCheck.getUsername(), updateCheck.getOldPassword());
        if (flag) {
            return CommonResult.success("true");
        }
        return CommonResult.success("false");
    }

    @ApiOperation("修改用户密码")
    @PostMapping("/updatePassword")
    public CommonResult<String> updatePassword(@RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.failed();
    }


    @ApiOperation(value = " 删除用户-备用的")
    @PostMapping("/delete")
    public CommonResult<Integer> deleteAdminById(Long id) {
        int count = adminService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("通过用户id获取相关角色")
    @GetMapping("/getRoleByAdminId")
    public CommonResult<Map<String, Object>> listMenu(@RequestParam("adminId") Long adminId) {
        Map<String, Object> roleList = adminService.getRoleByAdminId(adminId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("用户分配角色")
    @PostMapping("/adminToRole")
    public CommonResult<String> roleToAuth(@RequestBody AdminRoleRelationDTO adminRole) {
        int size = adminService.adminToRoleRelationship(adminRole);
        if (size > 0) {
            return CommonResult.success("成功");
        } else {
            return CommonResult.success("失败");
        }
    }

    @ApiOperation("密码初始化")
    @PostMapping("/pwdInit")
    public CommonResult<String> pwdInit(Long id) {
        int result = adminService.pwdInit(id);
        if (result > 0) {
            return CommonResult.success("成功");
        } else {
            return CommonResult.failed("失败");
        }
    }

    @ApiOperation("获取所有的员工信息[id, name]")
    @GetMapping("/adminAll")
    public CommonResult<List<Map<String, Object>>> adminAll() {
        List<Map<String, Object>> adminList = adminService.getAdminAll();
        if (adminList != null) {
            return CommonResult.success(adminList);
        }
        return CommonResult.failed();
    }


    @ApiOperation("当前用户角色")
    @GetMapping(value = "/getRoleIdListByUserId")
    public CommonResult<List<Long>> listRole(@RequestParam(value = "userId") Long userId) {

        List<Long> roleTreeByUserId = adminService.getRoleIdListByUserId(userId);
        return CommonResult.success(roleTreeByUserId);
    }
}

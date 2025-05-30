package com.yang.jxc.controller;

import com.yang.jxc.domain.dto.RoleMenuRelationDTO;
import com.yang.jxc.domain.entity.Role;
import com.yang.jxc.service.RoleService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "RoleController", description = "系统管理-角色")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "角色名-角色是否存在", notes = "校验角色名称")
    @GetMapping(value = "/checkRoleName")
    public CommonResult<String> checkRoleName(@RequestParam(value = "roleName") String roleName) {
        boolean result = roleService.checkRoleName(roleName);
        if (result) {
            return CommonResult.success("YES");
        } else {
            return CommonResult.success("NO");
        }
    }

    @ApiOperation("添加角色")
    @PostMapping(value = "/add")
    public CommonResult<String> add(@RequestBody Role role) {
        System.out.println("添加角色");
        int count = roleService.addOrUpdateRole(role);
        if (count > 0) {
            return CommonResult.success("添加成功");
        } else {
            return CommonResult.failed("添加失败");
        }
    }

    @ApiOperation("修改角色")
    @PostMapping(value = "/update/{id}")
    public CommonResult<Integer> update(@RequestBody Role role) {
        int count = roleService.update(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除角色>同时删除菜单关系")
    @PostMapping(value = "/delete")
    public CommonResult<String> delete(Long roleId) {
        boolean flag = roleService.delete(roleId);
        if (flag) {
            return CommonResult.success("true");
        }
        return CommonResult.failed("false");
    }

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Role>> listAll() {
        List<Role> roleList = roleService.list();
        return CommonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<Role>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(roleService.list(keyword, pageSize, pageNum));
    }

    @ApiOperation("修改角色状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult<Integer> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        Role xtRole = new Role();
        xtRole.setStatus(status);
        int count = roleService.update(xtRole);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

//    @ApiOperation("通过角色id获取相关菜单")
//    @RequestMapping(value = "/getRoleMenuById")
//    @ResponseBody
//    public CommonResult<Map<String,Object>> listMenu(Long roleId) {
//        Map<String,Object> roleList = roleService.getRoleMenuById(roleId);
//        return CommonResult.success(roleList);
//    }


    @ApiOperation("获取角色树")
    @GetMapping("/listTreeRole")
    public CommonResult<List<Map<String, Object>>> listTreeRole() {
        List<Map<String, Object>> roleList = roleService.listTreeRole();
        return CommonResult.success(roleList);
    }


    @ApiOperation("通过角色id获取相关菜单")
    @GetMapping(value = "/getMenuIdListByRoleId")
    public CommonResult<List<Long>> listMenu(@RequestParam(value = "roleId") Long roleId) {
        List<Long> roleTreeByUserId = roleService.getMenuIdListByRoleId(roleId);
        return CommonResult.success(roleTreeByUserId);
    }


    @ApiOperation("角色分配菜单")
    @PostMapping(value = "/roleToAuth")
    public CommonResult<String> roleToAuth(@RequestBody RoleMenuRelationDTO roleMenu) {
        int size = roleService.roleToAuth(roleMenu);
        if (size > 0) {
            return CommonResult.success("成功");
        } else {
            return CommonResult.success("失败");
        }
    }
}

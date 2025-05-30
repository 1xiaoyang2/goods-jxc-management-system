package com.yang.jxc.controller;

import com.yang.jxc.domain.dto.ShowMenu;
import com.yang.jxc.domain.entity.Menu;
import com.yang.jxc.service.MenuService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 说明
 * 登陆后需要显示菜单，通过用户--找到角色-调用角色-->查找菜单及其子菜单-->前端显示
 */


@RestController
@Api(tags = "MenuController", description = "系统管理-菜单")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @ApiOperation("添加菜单")
    @PostMapping(value = "/add")
    public CommonResult<Integer> create(@RequestBody Menu menu) {
        int count = menuService.create(menu);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改菜单")
    @PostMapping(value = "/update/{id}")
    public CommonResult<Integer> update(@PathVariable Long id, @RequestBody Menu menu) {
        int count = menuService.update(id, menu);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除菜单")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(@RequestParam("ids") List<Long> ids) {
        int count = menuService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改菜单状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult<Integer> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        Menu menu = new Menu();
        menu.setHidden(status);  
        int count = menuService.update(id, menu);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    @ApiOperation("根据角色名称分页获取菜单列表")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<Menu>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Menu> menuList = menuService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(menuList));
    }

    //通过角色获取菜单
    @ApiOperation("查询父菜单信息")
    @GetMapping(value = "/ListParent")
    public void ListMenuParent() {
        List<Menu> stringObjectMap = menuService.listMenuParent();
    }

    @ApiOperation("获取当前登陆用户的所有菜单")
    @GetMapping("/getShowMenu")
    public CommonResult<List<ShowMenu>> getShowMenu() {
        List<ShowMenu> showMenus = menuService.selectShowMenuByAdminName();
        return CommonResult.success(showMenus);
    }

    @ApiOperation("获取菜单树")
    @GetMapping("/listTreeMenu")
    public CommonResult<List<Map<String, Object>>> listTreeRole() {
        List<Map<String, Object>> menuList = menuService.listTreeMenu();
        return CommonResult.success(menuList);
    }
}

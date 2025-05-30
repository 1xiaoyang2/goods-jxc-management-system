package com.yang.jxc.controller;


import com.yang.jxc.domain.entity.Note;
import com.yang.jxc.service.NoteService;
import com.yang.jxc.utils.CommonPage;
import com.yang.jxc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "NoteController", description = "系统管理-笔记")
@RequestMapping("/bjNote")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @ApiOperation("添加笔记")
    @PostMapping(value = "/add")
    public CommonResult<Integer> create(@RequestBody Note note) {
        int count = noteService.create(note);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 问题获取不到前端传递的参数  @RequestParam(value ="title") post与get的问题
     *
     * @param title
     * @return
     */
    @ApiOperation("创建笔记，创建笔记名")
    @PostMapping(value = "/createByTitle")
    public CommonResult<Integer> createByTitle(String title) {
        int result = noteService.createByTitle(title);
        if (result > 0) {
            return CommonResult.success(result);
        }
        return CommonResult.failed("系统暂时只能创建8条 ");
    }

    @ApiOperation("更新笔记")
    @PostMapping(value = "/update")
    public CommonResult<String> update(@RequestBody Note note) {
        int count = noteService.update(note);
        if (count > 0) {
            return CommonResult.success("成功");
        }
        return CommonResult.failed();
    }


    @ApiOperation("获取当前用户的所有笔记")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Note>> listAll() {
        System.out.println("********************");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) token.getPrincipal();
        System.out.println("笔记-----------------------" + userName);
        List<Note> noteList = noteService.list(userName);

        Note Note = noteList.get(0);
        return CommonResult.success(noteList);
    }

    @ApiOperation("分页查询笔记")
    @GetMapping(value = "/listByName")
    public CommonResult<CommonPage<Note>> listByName(@RequestParam(value = "keyword", required = false) String keyword,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return CommonResult.success(noteService.listByName(keyword, pageSize, pageNum));
    }

    @ApiOperation("删除笔记")
    @PostMapping(value = "/deleteById")
    public CommonResult<String> deleteNote(Long id) {
        int count = noteService.delete(id);
        if (count > 0) {
            return CommonResult.success("成功");
        }
        return CommonResult.failed();
    }
}

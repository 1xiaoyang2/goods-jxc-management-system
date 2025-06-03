package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.Note;
import com.yang.jxc.mapper.NoteMapper;
import com.yang.jxc.service.NoteService;
import com.yang.jxc.utils.CommonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public int update(Note note) {
        note.setUpdateTime(LocalDateTime.now());
        return noteMapper.updateById(note);
    }

    @Override
    public int delete(Long id) {
        return noteMapper.deleteById(id);
    }

    //查询 分页获取当前用户的笔记
    @Override
    public CommonPage<Note> listByName(String keyword, Integer pageSize, Integer pageNum) {
        if(keyword != null){
            keyword = keyword.trim();
        }
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = token.getPrincipal().toString();
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Note::getTitle, keyword);
        wrapper.eq(Note::getUserName, username);
        IPage<Note> page = new Page<>(pageNum, pageSize);
        IPage<Note> noteIPage = noteMapper.selectPage(page, wrapper);
        return CommonPage.<Note>builder().list(noteIPage.getRecords()).total(noteIPage.getTotal()).build();
    }

    @Override
    public int createByTitle(String title) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = token.getPrincipal().toString();
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Note::getUserName, username);
        if (noteMapper.selectList(wrapper).size() > 8) {
            // 笔记数量超过8个, 创建失败
            return 0;
        }

        Note note = new Note();
        note.setCreateTime(LocalDateTime.now());
        note.setUserName(username);
        note.setTitle(title);
        return noteMapper.insert(note);
    }
}

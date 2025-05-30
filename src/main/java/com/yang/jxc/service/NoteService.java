package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Note;

import java.util.List;

/**
 * 笔记 service
 */
public interface NoteService {

    /**
     * 添加笔记
     */
    int create(Note note);

    /**
     * 修改笔记信息
     */
    int update(Note note);

    /**
     * 删除笔记
     */
    int delete(Long id);

    List<Note> list(String userName);

    /**
     * 分页获取所有笔记列表-------只能获取自己的
     */
    List<Note> listByName(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 创建新笔记
     *
     * @param title
     * @return
     */
    int createByTitle(String title);
}

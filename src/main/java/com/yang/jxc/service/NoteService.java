package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Note;
import com.yang.jxc.utils.CommonPage;

import java.util.List;

/**
 * 笔记 service
 */
public interface NoteService {
    /**
     * 创建新笔记
     *
     * @param title
     * @return
     */
    int createByTitle(String title);

    /**
     * 修改笔记信息
     */
    int update(Note note);

    /**
     * 删除笔记
     */
    int delete(Long id);

    /**
     * 分页获取所有笔记列表-------只能获取自己的
     */
    CommonPage<Note> listByName(String keyword, Integer pageSize, Integer pageNum);
}

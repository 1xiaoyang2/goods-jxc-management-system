package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.Log;
import com.yang.jxc.mapper.LogMapper;
import com.yang.jxc.service.LogService;
import com.yang.jxc.utils.CommonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :ygy
 * @description :
 * @create :2023-07-20 11:15:00
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public void save(Log log) {
         logMapper.insert(log);
    }

    @Override
    public CommonPage<Log> getList(String keyword , Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Log> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Log::getName, keyword);
        IPage<Log> page = new Page<>(pageNum, pageSize);
        IPage<Log> logIPage = logMapper.selectPage(page, wrapper);
        return CommonPage.<Log>builder().list(logIPage.getRecords()).total(logIPage.getTotal()).build();
    }
}

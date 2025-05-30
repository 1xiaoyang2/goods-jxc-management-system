package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Log;

import java.util.List;

/**
 * @description :
 */
public interface LogService {
    void save(Log sysLog);

    List<Log> getList(String keyword,Integer pageSize, Integer pageNum);
}

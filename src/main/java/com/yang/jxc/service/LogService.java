package com.yang.jxc.service;


import com.yang.jxc.domain.entity.Log;
import com.yang.jxc.utils.CommonPage;

import java.util.List;

/**
 * @description :
 */
public interface LogService {
    void save(Log sysLog);

    CommonPage<Log> getList(String keyword, Integer pageSize, Integer pageNum);
}

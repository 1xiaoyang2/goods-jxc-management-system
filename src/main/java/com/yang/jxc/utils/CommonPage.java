package com.yang.jxc.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用分页数据封装类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPage<T> {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> list;
}

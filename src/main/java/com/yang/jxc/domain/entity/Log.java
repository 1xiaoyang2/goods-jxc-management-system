package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Log implements Serializable {
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "操作")
    private String operation;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "执行时长（毫秒）")
    private Long time;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    private static final long serialVersionUID = 1L;
}
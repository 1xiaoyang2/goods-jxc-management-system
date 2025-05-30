package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ResourceCategory implements Serializable {
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    private static final long serialVersionUID = 1L;
}
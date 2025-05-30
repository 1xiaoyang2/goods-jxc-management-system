package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TableList implements Serializable {
    private Long id;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "业务模块")
    private String md;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "表的顺序即业务-菜单显示顺序")
    private Long sort;

    @ApiModelProperty(value = "模块业务的子业务显示顺序")
    private Long children;

    @ApiModelProperty(value = "备注")
    private String remark;

    private static final long serialVersionUID = 1L;
}
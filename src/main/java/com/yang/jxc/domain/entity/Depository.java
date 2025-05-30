package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Depository implements Serializable {
    private static final long serialVersionUID = 13214142423523523L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "仓库编号")
    private String number;

    @ApiModelProperty(value = "仓库名称")
    private String name;

    @ApiModelProperty(value = "仓库负责人")
    private String head;

    @ApiModelProperty(value = "仓库电话")
    private String storePhone;

    @ApiModelProperty(value = "仓库地址")
    private String address;

    @ApiModelProperty(value = "库存总容量")
    private Long stockTotal;

    @ApiModelProperty(value = "剩余容量")
    private Long surplus;

    @ApiModelProperty(value = "面积单位")
    private String area;

    @ApiModelProperty(value = "0 正常 1 停用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime buildDate;

    @ApiModelProperty(value = "备注")
    private String remark;
}
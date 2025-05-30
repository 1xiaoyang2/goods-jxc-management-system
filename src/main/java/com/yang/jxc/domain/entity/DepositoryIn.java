package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositoryIn implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "来源 可有也可无")
    private Integer sourceNumber;

    @ApiModelProperty(value = "入库编号")
    private Long inId;

    @ApiModelProperty(value = "入库仓库名")
    private String depository;

    @ApiModelProperty(value = "入库商名")
    private String shopName;

    @ApiModelProperty(value = "入库商品单价")
    private BigDecimal shopPrice;

    @ApiModelProperty(value = "入库商品数量")
    private Long shopNumber;

    @ApiModelProperty(value = "入库商品总价")
    private BigDecimal priceTotal;

    @ApiModelProperty(value = "单位规格 [个斤盒]")
    private String specs;

    @ApiModelProperty(value = "入库日期")
    private LocalDateTime date;

    @ApiModelProperty(value = "入库人")
    private String inUser;

    @ApiModelProperty(value = "供应商名")
    private String shopSupplier;

    @ApiModelProperty(value = "0已质检 1 未质检")
    private Integer isInspection;

    @ApiModelProperty(value = "0 已入库 1 未入库")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    private static final long serialVersionUID = 1L;
}
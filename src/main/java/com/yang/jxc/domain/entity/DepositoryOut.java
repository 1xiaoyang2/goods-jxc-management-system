package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositoryOut implements Serializable {
    @ApiModelProperty(value = "出库id")
    private Long id;

    @ApiModelProperty(value = "来源 id")
    private Long sourceNumber;

    @ApiModelProperty(value = "出库编号")
    private Long outId;

    @ApiModelProperty(value = "出库仓库")
    private String depository;

    @ApiModelProperty(value = "商品名称")
    private String shopName;

    @ApiModelProperty(value = "出库价格")
    private BigDecimal shopPrice;

    @ApiModelProperty(value = "商品数量")
    private Long shopNumber;

    @ApiModelProperty(value = "商品总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "规格")
    private String specs;

    @ApiModelProperty(value = "出库时间")
    private LocalDateTime date;

    @ApiModelProperty(value = "出库用户")
    private String outUser;

    @ApiModelProperty(value = "客户姓名")
    private String shopSupplier;

    @ApiModelProperty(value = "是否出库 0 出库 1不出库")
    private Integer status;

    @ApiModelProperty(value = "0 审核 1表示未审核")
    private Integer outInspection;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;
}
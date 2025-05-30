package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SaleExit implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "销售编号")
    private String number;

    @ApiModelProperty(value = "退购编号")
    private String exitNumber;

    @ApiModelProperty(value = "退购数量")
    private Long num;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "日期")
    private LocalDateTime time;

    @ApiModelProperty(value = "退购原因")
    private String reason;

    @ApiModelProperty(value = "状态 0 完成  1进行中")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    private static final long serialVersionUID = 1L;
}
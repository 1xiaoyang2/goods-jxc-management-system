package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Shop implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "商品名")
    private String name;

    @ApiModelProperty(value = "商品科类")
    private Long parentId;

    @ApiModelProperty(value = "商品数量")
    private Long shopNumber;

    @ApiModelProperty(value = "规格")
    private String specs;

    @ApiModelProperty(value = "市场价格")
    private BigDecimal marketPrice;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime buildDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    private static final long serialVersionUID = 1L;
}
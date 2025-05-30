package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopType implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "商品种类编号")
    private Long classId;

    @ApiModelProperty(value = "商品类型")
    private String shopType;

    @ApiModelProperty(value = "商品信息")
    private String info;

    private static final long serialVersionUID = 1L;
}
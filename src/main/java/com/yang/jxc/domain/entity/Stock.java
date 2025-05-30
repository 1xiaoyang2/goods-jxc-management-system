package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Stock implements Serializable {
    @ApiModelProperty(value = "序号 商品库存id")
    private Long id;

    @ApiModelProperty(value = "商品")
    private String shop;

    @ApiModelProperty(value = "商品类型")
    private String shopType;

    @ApiModelProperty(value = "库存量")
    private Long quantity;

    @ApiModelProperty(value = "规格 斤 、千克、个")
    private String specs;

    @ApiModelProperty(value = "仓库名")
    private String depository;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    private static final long serialVersionUID = 1L;
}
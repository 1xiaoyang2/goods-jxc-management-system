package com.yang.jxc.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("sale")
public class Sale implements Serializable {
    private static final long serialVersionUID = 182934729347893L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "销售编号")
    private String saleNumber;

    @ApiModelProperty(value = "销售人")
    private String saleUser;

    @ApiModelProperty(value = "销售商品")
    private String shop;

    @ApiModelProperty(value = "商品所属类")
    private String shopType;

    @ApiModelProperty(value = "销售客户，需要改成客户英文名")
    private String supplier;

    @ApiModelProperty(value = "数量")
    private Long num;

    @ApiModelProperty(value = "单位规格  个斤盒")
    private String specs;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "销售时间")
    private LocalDateTime time;

    @ApiModelProperty(value = "状态 0 完成 1进行中")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
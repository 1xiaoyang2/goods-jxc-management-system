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
@TableName("purchase_exit")
public class PurchaseExit implements Serializable {
    private static final long serialVersionUID = 182934729347893L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "采购编号")
    private String number;

    @ApiModelProperty(value = "退采编号")
    private String exitNumber;

    @ApiModelProperty(value = "退采数量")
    private Integer num;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "日期")
    private LocalDateTime time;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "状态 0 完成 1进行中  ")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "单位规格 个斤盒")
    private String specs;
}
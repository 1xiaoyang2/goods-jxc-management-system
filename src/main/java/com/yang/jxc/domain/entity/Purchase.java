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
@TableName("purchase")
public class Purchase implements Serializable {
    private static final long serialVersionUID = 182934729347893L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "采购编号")
    private String number;

    @ApiModelProperty(value = "采购人")
    private String purchaseUser;

    @ApiModelProperty(value = "采购商品")
    private String shop;

    @ApiModelProperty(value = "商品所属类")
    private String shopType;

    @ApiModelProperty(value = "采购供应商")
    private String supplier;

    @ApiModelProperty(value = "采购数量")
    private Long quantity;

    @ApiModelProperty(value = "采购价格")
    private BigDecimal price;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "采购时间")
    private LocalDateTime time;

    @ApiModelProperty(value = "状态 0 完成 1进行中")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "单位规格  个斤盒")
    private String specs;

    @ApiModelProperty(value = "0存在 1退货，退货后不显示，可增加查询按钮")
    private Integer isDestroy;

    @ApiModelProperty(value = "图片地址")
    private String images;

    @ApiModelProperty(value = "增值比")
    private Integer valueAttribute;
}
package com.yang.jxc.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("shop_type")
public class ShopType implements Serializable {
    private static final long serialVersionUID = 182934729347893L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品种类编号")
    private Long classId;

    @ApiModelProperty(value = "商品类型")
    private String shopType;

    @ApiModelProperty(value = "商品信息")
    private String info;
}
package com.yang.jxc.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("table_list")
public class TableList implements Serializable {
    private static final long serialVersionUID = 182934729347893L;
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "业务模块")
    private String md;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "表的顺序即业务-菜单显示顺序")
    private Long sort;

    @ApiModelProperty(value = "模块业务的子业务显示顺序")
    private Long children;

    @ApiModelProperty(value = "备注")
    private String remark;
}
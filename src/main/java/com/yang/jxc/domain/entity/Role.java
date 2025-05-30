package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Role implements Serializable {
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private String delFlag;

    @ApiModelProperty(value = "创建者")
    private String buildUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    private static final long serialVersionUID = 1L;
}
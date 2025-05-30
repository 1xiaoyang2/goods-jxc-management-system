package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminRoleRelation implements Serializable {
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long adminId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
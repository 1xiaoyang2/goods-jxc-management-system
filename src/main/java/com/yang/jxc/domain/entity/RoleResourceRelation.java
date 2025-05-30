package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleResourceRelation implements Serializable {
    private Long id;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "资源ID")
    private Long resourceId;

    private static final long serialVersionUID = 1L;
}
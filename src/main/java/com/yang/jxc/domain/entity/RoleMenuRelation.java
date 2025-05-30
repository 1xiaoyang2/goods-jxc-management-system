package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleMenuRelation implements Serializable {
    private Long id;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "菜单id")
    private Long menuId;

    private static final long serialVersionUID = 1L;
}
package com.yang.jxc.domain.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AdminRoleRelationDTO {
    @NotEmpty
    @ApiModelProperty(value = "角色id", required = true)
    private Long adminId;

    @NotEmpty
    @ApiModelProperty(value = "菜单id", required = true)
    private List<Long> roleIds;
}

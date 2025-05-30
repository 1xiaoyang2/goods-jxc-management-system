package com.yang.jxc.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


/**
 * 修改密码
 */
@Getter
@Setter
public class UpdateAdminPasswordParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}

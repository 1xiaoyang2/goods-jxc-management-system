package com.yang.jxc.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;



/**
 * 登陆  信息封装
 *
 * @NotEmpty 用在集合上面，一般用来校验List类型(不能注释枚举)，而且长度必须大于0
 *
 * @NotBlank 用在String上面，一般用来校验String类型不能为空，而且调用trim()后，长度必须大于0。
 *
 * @NotNull 用在所有类型上面，一般用来校验Integer类型不能为空，但可以为empty。

 */
@Data
@EqualsAndHashCode  //自动的给model bean实现equals方法和hashcode方法。
public class AdminLoginDTO {

    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}

package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Customer implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "客户姓名")
    private String name;

    @ApiModelProperty(value = "客户电话")
    private String phone;

    @ApiModelProperty(value = "客户地址")
    private String address;

    @ApiModelProperty(value = "客户邮箱")
    private String email;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "银行名")
    private String branch;

    @ApiModelProperty(value = "卡号")
    private String branchNo;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime buildDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
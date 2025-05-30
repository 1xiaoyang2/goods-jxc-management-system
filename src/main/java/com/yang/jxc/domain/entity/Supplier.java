package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Supplier implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "供应商名")
    private String supplierName;

    @ApiModelProperty(value = "负责人")
    private String head;

    @ApiModelProperty(value = "供应商电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "商品名称")
    private String shopName;

    @ApiModelProperty(value = "银行")
    private String branch;

    @ApiModelProperty(value = "银行账号")
    private String branchAccount;

    @ApiModelProperty(value = "供应商法人代表")
    private String supplierProxy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "其他")
    private String other;

    private static final long serialVersionUID = 1L;
}
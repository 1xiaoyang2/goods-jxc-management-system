package com.yang.jxc.domain.dto;

import com.yang.jxc.domain.entity.Purchase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class PurchaseToDepositoryInDTO {

    @ApiModelProperty(value = "入库仓库名",required = true)
    private String depositoryName; //入库仓库

    @ApiModelProperty(value = "入库的采购单" )
    private List<Purchase> purchaseList;
}

package com.yang.jxc.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Menu implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "菜单名称 label")
    private String title;

    @ApiModelProperty(value = "父级ID")
    private Long parentId;

    @ApiModelProperty(value = "菜单级数")
    private Integer level;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "前端名称")
    private String name;

    @ApiModelProperty(value = "前端图标")
    private String icon;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "状态 0正常 1隐藏")
    private Integer hidden;

    @ApiModelProperty(value = "访问地址")
    private String url;

    @ApiModelProperty(value = "vue路径")
    private String path;

    private static final long serialVersionUID = 1L;
}
package com.yang.jxc.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShowMenu {
    private  String name;
    private  String label;  //对应title
    private  String path;
    private  String icon;
    private  String url;
    private  List<ShowMenu> children;
}

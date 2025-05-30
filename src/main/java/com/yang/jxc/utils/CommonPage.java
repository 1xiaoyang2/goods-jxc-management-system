package com.yang.jxc.utils;


import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 通用分页数据封装类
 */
public class CommonPage<T> {
    /**
     * 当前页码索引
     */
    private Integer pageIndex;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {


        CommonPage<T> result = new CommonPage<T>();   //分页类
        PageInfo<T> pageInfo = new PageInfo<T>(list);  //需要分页的类 pagehelper mybatis

        result.setPageIndex(pageInfo.getPageNum());  //当前页码
        result.setTotalPage(pageInfo.getPages());   //总页数
        result.setPageSize(pageInfo.getPageSize());  //每页数量
        result.setTotal(pageInfo.getTotal());       //总条数
        result.setList(pageInfo.getList());

        System.out.println("mybatis分页封装******************");
        System.out.println("当前页码："+pageInfo.getPageNum()
                +"\t每页数量"+pageInfo.getPageSize()
                +"\t总页数"+pageInfo.getPages()
                +"\t总条数"+pageInfo.getTotal());

//        分页后的信息
        System.out.println("分页后的信息封装*********************");
        System.out.println("当前页码："+result.getPageIndex()
                +"\t每页数量"+result.getPageSize()
                +"\t总页数"+result.getTotalPage()
                +"\t总条数"+result.getTotal());
        System.out.println("******************************************");
        return result;
    }

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(Page<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageIndex(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());

        return result;
    }


    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}

package com.yang.jxc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.jxc.domain.entity.ShopType;
import org.apache.ibatis.annotations.Select;

public interface ShopTypeMapper extends BaseMapper<ShopType> {
    @Select("select * from shop_type where id = (select parant_id from shop where name = #{shopName})")
    ShopType selectShopTypeByName(String shopName);
}
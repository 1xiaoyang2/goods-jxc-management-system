package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.domain.entity.Shop;
import com.yang.jxc.domain.entity.ShopType;
import com.yang.jxc.mapper.ShopMapper;
import com.yang.jxc.mapper.ShopTypeMapper;
import com.yang.jxc.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门  service
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopTypeMapper shopTypeMapper;


    @Override
    public int create(Shop shop) {
        shop.setBuildDate(LocalDateTime.now());
        // dept.setAdminCount(0);
        //  role.setSort(0);
        return shopMapper.insert(shop);
    }

    @Override
    public int update(Shop shop) {
        shop.setUpdateDate(LocalDateTime.now());
        return shopMapper.updateById(shop);
    }

    @Override
    public int updateOrAddById(Shop shop) {
        if (shop.getId() != null && shop.getId() != 0) {//更新
            this.update(shop);
        } else {
            this.create(shop);
        }
        //先默认删除/修改 成功
        return 1;
    }

    @Override
    public int delete(Long id) {
        //  adminCacheService.delResourceListByRoleIds(ids);
        return shopMapper.deleteById(id);
    }

    @Override
    public List<Shop> list() {
        return null;
    }

/*
    @Override
    public List<Shop> list() {
        return shopMapper.selectByExample(new ShopExample());
    }
*/

    @Override
    public List<Shop> list(String keyword, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Shop> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Shop::getName, keyword);
        IPage<Shop> page = new Page<>(pageNum, pageSize);
        return shopMapper.selectList(page, wrapper);
    }

    @Override
    public ShopType selectShopTypeByName(String shopName) {
        return shopTypeMapper.selectShopTypeByName(shopName);

    }

    @Override
    public ArrayList<Map<String, Object>> getShopNameAll() {
        List<Shop> Shops = shopMapper.selectList(new LambdaQueryWrapper<>());
        ArrayList<Map<String, Object>> shopNameList = new ArrayList<>();
        if (Shops != null && !Shops.isEmpty()) {
            for (Shop shop : Shops) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", shop.getId());
                map.put("name", shop.getName());
                shopNameList.add(map);
            }
        }
        return shopNameList;
    }


    /**
     *  获取商品类型的
     * @return
     */
    @Override
    public List<ShopType> selectShopTypeList() {
        return  shopTypeMapper.selectList(new LambdaQueryWrapper<>());
    }
}

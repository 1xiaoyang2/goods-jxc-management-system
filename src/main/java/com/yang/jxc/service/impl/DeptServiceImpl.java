package com.yang.jxc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.jxc.annotation.SystemLog;
import com.yang.jxc.constant.AopLogConstant;
import com.yang.jxc.domain.entity.Dept;
import com.yang.jxc.mapper.DeptMapper;
import com.yang.jxc.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门  service
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @SystemLog(AopLogConstant.XTMD_3)
    @Override
    public int create(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setStatus(0);  //正常
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userName = token.getPrincipal().toString();
        dept.setCreateBy(userName);
        return deptMapper.insert(dept);
    }

    @SystemLog(AopLogConstant.XTMD_3)
    @Override
    public int update(Dept dept) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userName = token.getPrincipal().toString();
        dept.setUpdateTime(LocalDateTime.now());
        dept.setUpdateBy(userName);
        return deptMapper.updateById(dept);
    }

    @SystemLog(AopLogConstant.XTMD_3)
    @Override
    public int delete(Long id) {
        return deptMapper.deleteById(id);
    }

    @SystemLog(AopLogConstant.XTMD_3)
    @Override
    public List<Dept> list() {
        return deptMapper.selectList(new LambdaQueryWrapper<>());
    }


    @SystemLog(AopLogConstant.XTMD_3)
    @Override
    public List<Dept> list(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Dept::getDeptName, keyword);
        IPage<Dept> page = new Page<>(pageNum, pageSize);
        return deptMapper.selectList(page, wrapper);
    }

    @SystemLog(AopLogConstant.XTMD_3)
    @Override
    public boolean checkDeptName(String deptName) {
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dept::getDeptName, deptName);
        return !CollectionUtils.isEmpty(deptMapper.selectList(wrapper));
    }

    @SystemLog(AopLogConstant.XTMD_3)
    @Override
    public int updateOrAddById(Dept dept) {
        if (dept.getId() != null && dept.getId() != 0) { //更新
            this.update(dept);
        } else {
            this.create(dept);
        }
        return 1;
    }
}
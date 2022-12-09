package com.electronic.service.impl;


import com.electronic.mapper.BaseMapper;
import com.electronic.service.BaseService;
import com.electronic.utils.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected abstract BaseMapper<T> getEntityMapper();

    public Integer insert(T t) {
        return getEntityMapper().insert(t);
    }

    public void delete(Long id) {
        getEntityMapper().delete(id);
    }

    public Integer update(T t) {
        return getEntityMapper().update(t);
    }

    public T getById(Serializable id) {
        return getEntityMapper().getById(id);
    }

    public PageInfo<T> selectAllAsPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(getEntityMapper().selectAllAsPage(filters), 10);
    }
}

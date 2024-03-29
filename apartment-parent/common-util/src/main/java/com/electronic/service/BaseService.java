package com.electronic.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

public interface BaseService<T> {
    Integer insert(T t);

    void delete(Long id);

    Integer update(T t);

    T getById(Serializable id);

    PageInfo<T> selectAllAsPage(Map<String, Object> filters);
}

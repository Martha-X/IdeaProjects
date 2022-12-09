package com.electronic.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
    
    /** 
     * @Description 创建一个实体
     * @Param t
     * @Return java.lang.Integer
     * @Author electroNic
     * @Date 2022/11/29 4:55
     */
    Integer insert(T t);


    /**
     * @Description 逻辑删除一个实体
     * @Param id 标识ID 可以是自增长ID，也可以是唯一标识
     * @Return void
     * @Author electroNic
     * @Date 2022/11/29 4:55
     */
    void delete(Serializable id);

    /** 
     * @Description 修改一个实体
     * @Param t
     * @Return java.lang.Integer
     * @Author electroNic
     * @Date 2022/11/29 4:56
     */
    Integer update(T t);

    /** 
     * @Description 通过一个标识ID 获取一个唯一实体
     * @Param id
     * @Return T
     * @Author electroNic
     * @Date 2022/11/29 4:56
     */
    T getById(Serializable id);

    /** 
     * @Description 根据条件进行分页查询
     * @Param filters
     * @Return com.github.pagehelper.Page<T>
     * @Author electroNic
     * @Date 2022/11/29 4:58
     */
    List<T> selectAllAsPage(Map<String, Object> params);
}

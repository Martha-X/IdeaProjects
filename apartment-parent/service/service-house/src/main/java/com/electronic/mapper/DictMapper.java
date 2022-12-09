package com.electronic.mapper;

import com.electronic.entity.Dict;

import java.util.List;

public interface DictMapper {
    // 根据父级id查询当前父节点下的所有子结点
    List<Dict> findZnodes(Long id);

    // 根据父级id判断该节点是否是父节点
    Integer count(Long id);

    // 根据编码获取一个Dict对象
    Dict getDictByDictCode(String dictCode);

    // 根据id获取name
    String getNameById(Long id);

}

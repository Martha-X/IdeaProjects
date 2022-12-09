package com.electronic.service;

import com.electronic.entity.Dict;
import com.electronic.result.Result;

import java.util.List;

public interface DictService {
    // 查询数据字典中的数据,根据zTree进行渲染
    Result findZnodes(Long id);

    // 根据编码获取该结点下的所有子结点
    List<Dict> getDictListByDictCode(String dictCode);

    // 根据父级id获取该结点下的所有子结点
    List<Dict> getDictListByParentId(Long parentId);

    String getNameById(Long id);
}

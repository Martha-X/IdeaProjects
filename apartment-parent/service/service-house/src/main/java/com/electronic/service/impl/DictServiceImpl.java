package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.Dict;
import com.electronic.mapper.DictMapper;
import com.electronic.result.Result;
import com.electronic.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public Result findZnodes(Long id) {
        // 根据父级id查询该结点下的所有子结点
        List<Dict> dicts = dictMapper.findZnodes(id);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> resultMap = null;
        for (Dict dict : dicts) {
            resultMap = new HashMap<>();
            resultMap.put("id", dict.getId());
            resultMap.put("name", dict.getName());
            resultMap.put("isParent", isParent(dict.getId()));
            result.add(resultMap);
        }
        return Result.ok(result);
    }

    @Override
    public List<Dict> getDictListByDictCode(String dictCode) {
        Dict dictByDictCode = dictMapper.getDictByDictCode(dictCode);
        if (dictByDictCode == null)
            return null;
        List<Dict> znodes = dictMapper.findZnodes(dictByDictCode.getId());
        return znodes;
    }

    @Override
    public List<Dict> getDictListByParentId(Long parentId) {
        return dictMapper.findZnodes(parentId);
    }

    @Override
    public String getNameById(Long id) {
        return dictMapper.getNameById(id);
    }

    /**
     * @Description 判断该结点是否是一个叶子节点
     * @Param id
     * @Return boolean
     * @Author electroNic
     * @Date 2022/11/30 5:04
     */
    private boolean isParent(Long id) {
        Integer count = dictMapper.count(id);
        return count > 0;
    }
}

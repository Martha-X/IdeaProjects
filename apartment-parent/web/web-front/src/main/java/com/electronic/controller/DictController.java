package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.Dict;
import com.electronic.result.Result;
import com.electronic.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    @RequestMapping("/findListByDictCode/{code}")
    public Result findListByDictCode(@PathVariable("code") String code) {
        List<Dict> dictListByDictCode = dictService.getDictListByDictCode(code);
        return Result.ok(dictListByDictCode);
    }

    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId) {
        List<Dict> dictListByParentId = dictService.getDictListByParentId(areaId);
        return Result.ok(dictListByParentId);
    }
}

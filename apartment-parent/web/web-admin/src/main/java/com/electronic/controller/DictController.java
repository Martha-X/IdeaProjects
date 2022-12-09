package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.Dict;
import com.electronic.result.Result;
import com.electronic.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    /**
     * @Description 去到数据字典展示的页面
     * @Param none
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/30 4:52
     */
    @RequestMapping("/")
    public String index() {
        return "dict/index";
    }

    @ResponseBody
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return dictService.findZnodes(id);
    }

    @ResponseBody
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId) {
        List<Dict> dictListByParentId = dictService.getDictListByParentId(areaId);
        return Result.ok(dictListByParentId);
    }
}

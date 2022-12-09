package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.Community;
import com.electronic.entity.Dict;
import com.electronic.service.CommunityService;
import com.electronic.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    public static final String SUCCESS_PAGE = "common/success";
    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        Map<String, Object> params = getParams(request);
        mv.addObject("filters", params);
        PageInfo<Community> communityPageInfo = communityService.selectAllAsPage(params);
        mv.addObject("page", communityPageInfo);
        // 根据编码获取北京省所有的区域,第一次访问则默认加载北京区域
        List<Dict> areaList = dictService.getDictListByDictCode("beijing");
        mv.addObject("areaList", areaList);
        mv.setViewName("community/index");
        return mv;
    }

    /**
     * @Description 去添加小区页面, 携带区域数据回显
     * @Param none
     * @Return org.springframework.web.servlet.ModelAndView
     * @Author electroNic
     * @Date 2022/12/1 16:20
     */
    @RequestMapping("/create")
    public ModelAndView toCreatePage() {
        ModelAndView mv = new ModelAndView();
        List<Dict> areaList = dictService.getDictListByDictCode("beijing");
        mv.addObject("areaList", areaList);
        mv.setViewName("community/create");
        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView save(Community community) {
        ModelAndView mv = new ModelAndView();
        communityService.insert(community);
        mv.addObject("message", "添加小区成功");
        mv.setViewName(SUCCESS_PAGE);
        return mv;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView toEditPage(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        List<Dict> areaList = dictService.getDictListByDictCode("beijing");
        Community community = communityService.getById(id);
        mv.addObject("areaList", areaList);
        mv.addObject("community", community);
        mv.setViewName("community/edit");
        return mv;
    }

    @RequestMapping("/update")
    public ModelAndView update(Community community) {
        ModelAndView mv = new ModelAndView();
        communityService.update(community);
        mv.addObject("message", "修改小区成功");
        mv.setViewName(SUCCESS_PAGE);
        return mv;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        communityService.delete(id);
        return "redirect:/community/";
    }
}

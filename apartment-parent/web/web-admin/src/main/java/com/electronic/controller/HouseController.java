package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.*;
import com.electronic.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    public static final String SUCCESS_PAGE = "common/success";
    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;


    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // 获取请求参数
        Map<String, Object> params = getParams(request);
        // 分页查询所有房源信息
        PageInfo<House> housePageInfo = houseService.selectAllAsPage(params);
        // 获取所有房源信息信息
        List<Community> communityList = communityService.getAllCommunities();
        // 获取所有户型
        List<Dict> houseTypeList = dictService.getDictListByDictCode("houseType");
        // 获取楼层
        List<Dict> floorList = dictService.getDictListByDictCode("floor");
        // 获取建筑结构
        List<Dict> buildStructureList = dictService.getDictListByDictCode("buildStructure");
        // 获取朝向
        List<Dict> directionList = dictService.getDictListByDictCode("direction");
        // 获取装修情况
        List<Dict> decorationList = dictService.getDictListByDictCode("decoration");
        // 获取房屋用途
        List<Dict> houseUseList = dictService.getDictListByDictCode("houseUse");
        mv.addObject("filters", params);
        mv.addObject("page", housePageInfo);
        mv.addObject("communityList", communityList);
        mv.addObject("houseTypeList", houseTypeList);
        mv.addObject("floorList", floorList);
        mv.addObject("buildStructureList", buildStructureList);
        mv.addObject("directionList", directionList);
        mv.addObject("decorationList", decorationList);
        mv.addObject("houseUseList", houseUseList);
        mv.setViewName("house/index");
        return mv;
    }

    @RequestMapping("/create")
    public ModelAndView toCreatePage() {
        ModelAndView mv = new ModelAndView();
        // 获取所有房源信息信息
        List<Community> communityList = communityService.getAllCommunities();
        // 获取所有户型
        List<Dict> houseTypeList = dictService.getDictListByDictCode("houseType");
        // 获取楼层
        List<Dict> floorList = dictService.getDictListByDictCode("floor");
        // 获取建筑结构
        List<Dict> buildStructureList = dictService.getDictListByDictCode("buildStructure");
        // 获取朝向
        List<Dict> directionList = dictService.getDictListByDictCode("direction");
        // 获取装修情况
        List<Dict> decorationList = dictService.getDictListByDictCode("decoration");
        // 获取房屋用途
        List<Dict> houseUseList = dictService.getDictListByDictCode("houseUse");
        mv.addObject("communityList", communityList);
        mv.addObject("houseTypeList", houseTypeList);
        mv.addObject("floorList", floorList);
        mv.addObject("buildStructureList", buildStructureList);
        mv.addObject("directionList", directionList);
        mv.addObject("decorationList", decorationList);
        mv.addObject("houseUseList", houseUseList);
        mv.setViewName("house/create");
        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView save(House house) {
        ModelAndView mv = new ModelAndView();
        houseService.insert(house);
        mv.addObject("message", "添加房源信息成功");
        mv.setViewName(SUCCESS_PAGE);
        return mv;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView toEditPage(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        House house = houseService.getById(id);
        mv.addObject("house", house);
        // 获取所有房源信息信息
        List<Community> communityList = communityService.getAllCommunities();
        // 获取所有户型
        List<Dict> houseTypeList = dictService.getDictListByDictCode("houseType");
        // 获取楼层
        List<Dict> floorList = dictService.getDictListByDictCode("floor");
        // 获取建筑结构
        List<Dict> buildStructureList = dictService.getDictListByDictCode("buildStructure");
        // 获取朝向
        List<Dict> directionList = dictService.getDictListByDictCode("direction");
        // 获取装修情况
        List<Dict> decorationList = dictService.getDictListByDictCode("decoration");
        // 获取房屋用途
        List<Dict> houseUseList = dictService.getDictListByDictCode("houseUse");
        mv.addObject("communityList", communityList);
        mv.addObject("houseTypeList", houseTypeList);
        mv.addObject("floorList", floorList);
        mv.addObject("buildStructureList", buildStructureList);
        mv.addObject("directionList", directionList);
        mv.addObject("decorationList", decorationList);
        mv.addObject("houseUseList", houseUseList);
        mv.setViewName("house/edit");
        return mv;
    }

    @RequestMapping("/update")
    public ModelAndView update(House house) {
        ModelAndView mv = new ModelAndView();
        houseService.update(house);
        mv.addObject("message", "修改房源信息成功");
        mv.setViewName(SUCCESS_PAGE);
        return mv;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        houseService.delete(id);
        return "redirect:/house/";
    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id,
                          @PathVariable("status") Integer status) {
        houseService.publish(id, status);
        return "redirect:/house/";
    }

    @RequestMapping("/{id}")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        // 根据id查询房源信息
        House house = houseService.getById(id);
        // 根据house中的小区id查询小区
        Community community = communityService.getById(house.getCommunityId());
        // 查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(house.getId(), 1);
        List<HouseImage> houseImage2List = houseImageService.getHouseImagesByHouseIdAndType(house.getId(), 2);
        // 查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getBrokerByHouseId(house.getId());
        // 查询房东
        List<HouseUser> houseUserList = houseUserService.getHouseUserByHouseId(house.getId());
        mv.addObject("house", house);
        mv.addObject("community", community);
        mv.addObject("houseImage1List", houseImage1List);
        mv.addObject("houseImage2List", houseImage2List);
        mv.addObject("houseBrokerList", houseBrokerList);
        mv.addObject("houseUserList", houseUserList);
        mv.setViewName("house/show");
        return mv;
    }
}

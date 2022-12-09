package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.*;
import com.electronic.result.Result;
import com.electronic.service.*;
import com.electronic.vo.HouseQueryVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {
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
    private UserFollowService userFollowService;

    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result pageList(@PathVariable("pageNum") Integer pageNum,
                           @PathVariable("pageSize") Integer pageSize,
                           @RequestBody HouseQueryVo houseQueryVo) {
        // 前端分页条件查询
        Result result = houseService.selectPageList(pageNum, pageSize, houseQueryVo);
        return result;
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id, HttpSession session) {
        //查询房源信息
        House house = houseService.getById(id);
        // 查询房源所在小区信息
        Community community = communityService.getById(house.getCommunityId());
        // 查询经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.getBrokerByHouseId(id);
        // 查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(id, 1);

        Map<String, Object> map = new HashMap<>();
        map.put("house", house);
        map.put("community", community);
        map.put("houseBrokerList", houseBrokerList);
        map.put("houseImage1List", houseImage1List);

        boolean isFollow = false;
        //关注业务后续补充，当前默认返回未关注
        UserInfo user = (UserInfo) session.getAttribute("USER");
        if (user != null) {
            // 证明已经登录
            isFollow = userFollowService.isFollowed(user.getId(), id);
        }
        map.put("isFollow", isFollow);
        return Result.ok(map);
    }
}

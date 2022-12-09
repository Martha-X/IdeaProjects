package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.UserInfo;
import com.electronic.result.Result;
import com.electronic.service.UserFollowService;
import com.electronic.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    @Reference
    private UserFollowService userFollowService;

    /**
     * 关注房源
     *
     * @param houseId
     * @param request
     * @return
     */
    @GetMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        // 调用关注房源方法
        userFollowService.follow(userId, houseId);
        return Result.ok();
    }

    // 查询我的关注
    @GetMapping(value = "/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum,
                               @PathVariable Integer pageSize,
                               HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userId);
        return Result.ok(pageInfo);
    }

    @GetMapping("auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id, HttpServletRequest request){
        userFollowService.cancelFollow(id);
        return Result.ok();
    }
}

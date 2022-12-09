package com.electronic.service;

import com.electronic.entity.UserFollow;
import com.electronic.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

public interface UserFollowService extends BaseService<UserFollow>{
    void follow(Long userId, Long houseId);

    boolean isFollowed(Long id, Long id1);

    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId);

    void cancelFollow(Long id);
}

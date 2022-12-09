package com.electronic.mapper;

import com.electronic.entity.UserFollow;
import com.electronic.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface UserFollowMapper extends BaseMapper<UserFollow> {
    Integer isFollowed(@Param("id") Long id, @Param("houseId") Long houseId);

    Page<UserFollowVo> findListPage(Long userId);
}

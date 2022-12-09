package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.UserFollow;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.UserFollowMapper;
import com.electronic.service.DictService;
import com.electronic.service.UserFollowService;
import com.electronic.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {
    @Autowired
    private UserFollowMapper userFollowMapper;
    @Reference
    private DictService dictService;

    @Override
    protected BaseMapper<UserFollow> getEntityMapper() {
        return userFollowMapper;
    }

    @Override
    public void follow(Long userId, Long houseId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setHouseId(houseId);
        userFollowMapper.insert(userFollow);
    }

    @Override
    public boolean isFollowed(Long id, Long houseId) {
        return userFollowMapper.isFollowed(id, houseId) > 0;
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowMapper.findListPage(userId);
        List<UserFollowVo> list = page.getResult();
        for (UserFollowVo userFollowVo : list) {
            //户型：
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            //楼层
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            //朝向：
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<UserFollowVo>(page, 10);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowMapper.delete(id);
    }
}

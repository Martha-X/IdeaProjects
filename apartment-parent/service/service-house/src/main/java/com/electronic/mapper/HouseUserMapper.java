package com.electronic.mapper;

import com.electronic.entity.HouseUser;

import java.util.List;

public interface HouseUserMapper extends BaseMapper<HouseUser>{
    // 根据房源id查询房东信息
    List<HouseUser> getHouseUserByHouseId(Long houseId);
}

package com.electronic.service;

import com.electronic.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {
    // 根据房源id查询房东信息
    List<HouseUser> getHouseUserByHouseId(Long houseId);
}

package com.electronic.service;

import com.electronic.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker>{
    // 根据房源id查询房源经纪人
    List<HouseBroker> getBrokerByHouseId(Long id);
}

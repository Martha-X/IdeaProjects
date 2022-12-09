package com.electronic.mapper;

import com.electronic.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerMapper extends BaseMapper<HouseBroker>{
    // 根据房源id查询房源经纪人
    List<HouseBroker>  getBrokerByHouseId(Long id);
}

package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.HouseBroker;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.HouseBrokerMapper;
import com.electronic.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerMapper houseBrokerMapper;

    @Override
    protected BaseMapper<HouseBroker> getEntityMapper() {
        return houseBrokerMapper;
    }

    @Override
    public List<HouseBroker> getBrokerByHouseId(Long id) {
        return houseBrokerMapper.getBrokerByHouseId(id);
    }
}

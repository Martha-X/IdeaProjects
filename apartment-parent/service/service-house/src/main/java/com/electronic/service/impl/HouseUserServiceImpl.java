package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.HouseUser;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.HouseUserMapper;
import com.electronic.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {
    @Autowired
    private HouseUserMapper houseUserMapper;

    @Override
    protected BaseMapper<HouseUser> getEntityMapper() {
        return houseUserMapper;
    }

    @Override
    public List<HouseUser> getHouseUserByHouseId(Long houseId) {
        return houseUserMapper.getHouseUserByHouseId(houseId);
    }
}

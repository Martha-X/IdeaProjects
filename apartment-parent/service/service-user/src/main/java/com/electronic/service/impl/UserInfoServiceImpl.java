package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.UserInfo;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.UserInfoMapper;
import com.electronic.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserInfoService.class)
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    protected BaseMapper<UserInfo> getEntityMapper() {
        return userInfoMapper;
    }

    @Override
    public UserInfo getByPhone(String phone) {
        return userInfoMapper.getByPhone(phone);
    }
}

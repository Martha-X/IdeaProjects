package com.electronic.mapper;

import com.electronic.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo>{
    UserInfo getByPhone(String phone);
}

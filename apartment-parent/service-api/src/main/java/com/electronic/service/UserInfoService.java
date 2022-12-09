package com.electronic.service;

import com.electronic.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo>{
    UserInfo getByPhone(String phone);
}

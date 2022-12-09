package com.electronic.mapper;

import com.electronic.entity.Admin;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin>{
    List<Admin> getAll();

    Admin getAdminByUserName(String username);
}

package com.electronic.mapper;

import com.electronic.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role>{
    List<Role> selectAll();
}

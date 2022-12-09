package com.electronic.service;

import com.electronic.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role>{
    // 查询所有角色
    List<Role> selectAll();

    // 根据用户id查询用户所具有的角色
    Map<String,List<Role>> selectRolesByAdminId(Long adminId);

    void assignRole(Long adminId, Long[] roleIds);
}
package com.electronic.mapper;

import com.electronic.entity.RolePermission;

public interface RolePermissionMapper extends BaseMapper<RolePermission>{
    void deletePermissionIdsByRoleId(Long roleId);
}

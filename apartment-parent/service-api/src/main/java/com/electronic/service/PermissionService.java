package com.electronic.service;

import com.electronic.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {
    List<Map<String, Object>> selectPermissionsByRoleId(Long roleId);

    void assignPermission(Long roleId, Long[] permissionIds);

    // 根据用户id获取用户对应的左侧菜单
    List<Permission> getMenuPermissionsByAdminId(Long adminId);

    List<String> getPermissionCodesByAdminId(Long adminId);
}

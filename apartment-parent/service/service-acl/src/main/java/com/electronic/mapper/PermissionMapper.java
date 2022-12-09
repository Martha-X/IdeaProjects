package com.electronic.mapper;

import com.electronic.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectAll();

    List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);

    List<Permission> getMenuPermissionsByAdminId(@Param("adminId") Long adminId);

    List<String> getPermissionCodesByAdminId(@Param("adminId") Long adminId);

    List<String> getAllPermissionCodes();
}

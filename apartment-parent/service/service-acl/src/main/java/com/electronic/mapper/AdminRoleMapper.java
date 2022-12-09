package com.electronic.mapper;

import com.electronic.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    List<Long> selectRoleIdsByAdminId(@Param("adminId") Long adminId);

    void deleteRoleIdsByAdminId(@Param("adminId") Long adminId);
}

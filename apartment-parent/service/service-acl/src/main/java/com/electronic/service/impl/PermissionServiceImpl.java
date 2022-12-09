package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.Permission;
import com.electronic.entity.RolePermission;
import com.electronic.helper.PermissionHelper;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.PermissionMapper;
import com.electronic.mapper.RolePermissionMapper;
import com.electronic.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> selectPermissionsByRoleId(Long roleId) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 获取所有的权限
        List<Permission> permissions = permissionMapper.selectAll();
        // 根据角色id获取已分配的权限的权限id
        List<Long> permissionIds = permissionMapper.selectPermissionIdsByRoleId(roleId);
        // 遍历所有权限
        for (Permission permission : permissions) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", permission.getId());
            resultMap.put("pId", permission.getParentId());
            resultMap.put("name", permission.getName());
            // 判断当前权限id是否是已分配的id
            resultMap.put("checked", permissionIds.contains(permission.getId()));
            resultList.add(resultMap);
        }
        return resultList;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        // 先删除后添加
        rolePermissionMapper.deletePermissionIdsByRoleId(roleId);
        // 遍历所有权限id进行保存
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId);
            rolePermission.setRoleId(roleId);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    public List<Permission> getMenuPermissionsByAdminId(Long adminId) {
        List<Permission> permissionList = null;
        // 判断是否为系统管理员
        if (adminId == 1)
            // 如果是系统管理员则直接获取所有权限
            permissionList = permissionMapper.selectAll();
        else
            // 若不是系统管理原则根据用户标识查询菜单栏
            permissionList = permissionMapper.getMenuPermissionsByAdminId(adminId);
        // 使用工具类将权限列表list转为树形结构
        List<Permission> build = PermissionHelper.build(permissionList);
        return build;
    }

    @Override
    public List<String> getPermissionCodesByAdminId(Long adminId) {
        // 若是系统管理员则获取所有权限
        if(adminId == 1)
            return permissionMapper.getAllPermissionCodes();
        return permissionMapper.getPermissionCodesByAdminId(adminId);
    }
}

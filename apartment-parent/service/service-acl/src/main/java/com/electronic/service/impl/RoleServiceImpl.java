package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.AdminRole;
import com.electronic.entity.Role;
import com.electronic.mapper.AdminRoleMapper;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.RoleMapper;
import com.electronic.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    protected BaseMapper<Role> getEntityMapper() {
        return this.roleMapper;
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public Map<String, List<Role>> selectRolesByAdminId(Long adminId) {
        // 获取所有角色(包含角色id)
        List<Role> roles = roleMapper.selectAll();
        // 通过用户标识获取当前用户已分配角色的角色id
        List<Long> roleIds = adminRoleMapper.selectRoleIdsByAdminId(adminId);
        //  将所有角色进行拆分,分为已分配和未分配两部分
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        for (Role role : roles) {
            // 若包含已分配的角色id则将当前已分配角色放进assginRoleList
            if (roleIds.contains(role.getId())) {
                assginRoleList.add(role);
            } else {
                noAssginRoleList.add(role);
            }
        }
        Map<String, List<Role>> map = new HashMap<>();
        map.put("noAssginRoleList", noAssginRoleList);
        map.put("assginRoleList", assginRoleList);
        return map;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        // 先根据用户id将已分配角色逻辑删除
        adminRoleMapper.deleteRoleIdsByAdminId(adminId);
        // 遍历所有的角色id,逐一保存
        for (Long roleId : roleIds) {
            if (roleId == null)
                continue;
            AdminRole adminRole = new AdminRole();
            adminRole.setRoleId(roleId);
            adminRole.setAdminId(adminId);
            adminRoleMapper.insert(adminRole);
        }
    }
}

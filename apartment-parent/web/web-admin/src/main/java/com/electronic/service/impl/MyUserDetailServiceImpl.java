package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.Admin;
import com.electronic.service.AdminService;
import com.electronic.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    // 用户登录时会调用该方法,用户名即参数名
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByUserName(username);
        if (admin == null)
            throw new UsernameNotFoundException("用户不存在!");
        // 获取用户所有的权限
        List<String> authorities = permissionService.getPermissionCodesByAdminId(admin.getId());
        // 创建一个用于授权的集合来保存遍历的每一个权限
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // 遍历得到每一个权限
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return new User(username, admin.getPassword(), grantedAuthorities);
    }
}

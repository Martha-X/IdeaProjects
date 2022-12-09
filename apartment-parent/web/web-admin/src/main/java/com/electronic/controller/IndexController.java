package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.Admin;
import com.electronic.entity.Permission;
import com.electronic.service.AdminService;
import com.electronic.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    /**
     * @return java.lang.String
     * @Description 去首页
     * @Param none
     * @author electroNic
     * @date 2022/11/29 0:11
     */
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        // 从spring security容器中获取admin对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        // 调用adminService中根据用户名获取admin对象
        Admin admin = adminService.getAdminByUserName(user.getUsername());
        System.out.println(admin.getId());
        // 根据用户id获取用户对应的权限菜单
        List<Permission> permissionList = permissionService.getMenuPermissionsByAdminId(admin.getId());
        permissionList.forEach(System.out::println);
        mv.addObject("admin", admin);
        mv.addObject("permissionList", permissionList);
        mv.setViewName("frame/index");
        return mv;
    }

    /**
     * @return java.lang.String
     * @Description 去主页面
     * @Param none
     * @author electroNic
     * @date 2022/11/29 0:11
     */
    @RequestMapping("/main")
    public String main() {
        return "frame/main";
    }

    @RequestMapping("/login")
    public String login() {
        return "frame/login";
    }

    @RequestMapping("/accessDenied")
    public String toAccessDeniedPage() {
        return "frame/access-deny";
    }


}

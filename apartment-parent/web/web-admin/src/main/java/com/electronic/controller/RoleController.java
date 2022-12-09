package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.Role;
import com.electronic.service.PermissionService;
import com.electronic.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    // 成功页面的路径,定义为常量,可以复用
    private static final String SUCCESS_PAGE = "common/success";

    @Reference
    private RoleService roleService;
    @Reference
    private PermissionService permissionService;

    /**
     * @Description 分页且带条件查询所有角色信息
     * @Param map
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/29 1:02
     */
    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        // 获取请求参数
        Map<String, Object> params = getParams(request);
        PageInfo<Role> rolesPage = roleService.selectAllAsPage(params);
        ModelAndView mv = new ModelAndView();
        mv.addObject("filters", params);
        mv.addObject("page", rolesPage);
        mv.setViewName("role/index");
        return mv;
    }

    /**
     * @Description 去创建角色页面
     * @Param none
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/29 0:45
     */
    @RequestMapping("/create")
    public String toCreatePage() {
        return "role/create";
    }

    /**
     * @Description 执行创建角色操作
     * @Param role
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/29 1:01
     */
    @RequestMapping("/save")
    public ModelAndView save(Role role) {
        roleService.insert(role);
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "创建角色成功");
        mv.setViewName(SUCCESS_PAGE);
        return mv;
    }

    /**
     * @Description 根据角色id进行逻辑删除
     * @Param roleId
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/29 3:22
     */
    @PreAuthorize("hasAuthority('role.delete')") // 只有role.delete权限才能执行该方法
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId) {
        roleService.delete(roleId);
        // 删除之后直接重定向刷新当前页即可
        return "redirect:/role/";
    }

    /**
     * @Description 根据角色id查询角色, 并去往角色修改页面回显
     * @Param roleId
     * @Return org.springframework.web.servlet.ModelAndView
     * @Author electroNic
     * @Date 2022/11/29 3:31
     */
    @RequestMapping("/edit/{roleId}")
    public ModelAndView toEditPage(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("role", role);
        mv.setViewName("role/edit");
        return mv;
    }

    /**
     * @Description 执行修改角色操作
     * @Param none
     * @Return org.springframework.web.servlet.ModelAndView
     * @Author electroNic
     * @Date 2022/11/29 3:36
     */
    @RequestMapping("/update")
    public ModelAndView update(Role role) {
        roleService.update(role);
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "修改角色成功");
        mv.setViewName(SUCCESS_PAGE);
        return mv;
    }

    // 去分配权限的页面
    @RequestMapping("/assignShow/{roleId}")
    public ModelAndView assignShow(@PathVariable("roleId") Long roleId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleId", roleId);
        // 根据角色id获取权限
        List<Map<String, Object>> zNodes = permissionService.selectPermissionsByRoleId(roleId);
        mv.addObject("zNodes", zNodes);
        mv.setViewName("role/assignShow");
        return mv;
    }

    @RequestMapping("/assignPermission")
    public String assignPermission(Long roleId, Long[] permissionIds) {
        permissionService.assignPermission(roleId, permissionIds);
        return "common/success";
    }

}

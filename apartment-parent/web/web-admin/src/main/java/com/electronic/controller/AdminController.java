package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.Admin;
import com.electronic.entity.Role;
import com.electronic.result.Result;
import com.electronic.service.AdminService;
import com.electronic.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    public static final String SUCCESS_PAGE = "common/success";
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @Description 分页且带条件查询所有用户信息
     * @Param map
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/29 1:02
     */
    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        // 获取请求参数
        Map<String, Object> params = getParams(request);
        PageInfo<Admin> adminsPage = adminService.selectAllAsPage(params);
        ModelAndView mv = new ModelAndView();
        mv.addObject("filters", params);
        mv.addObject("page", adminsPage);
        mv.setViewName("admin/index");
        return mv;
    }

    /**
     * @Description 去创建用户页面
     * @Param none
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/29 0:45
     */
    @RequestMapping("/create")
    public String toCreatePage() {
        return "admin/create";
    }

    /**
     * @Description 执行创建用户操作
     * @Param admin
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/29 1:01
     */
    @RequestMapping("/save")
    public ModelAndView save(Admin admin) {
        // 在添加用户的时候获取密码加密器对密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.insert(admin);
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "创建用户成功");
        mv.setViewName(SUCCESS_PAGE);
        return mv;
    }

    /**
     * @Description 根据用户id进行逻辑删除
     * @Param adminId
     * @Return java.lang.String
     * @Author electroNic
     * @Date 2022/11/29 3:22
     */
    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable("adminId") Long adminId) {
        adminService.delete(adminId);
        // 删除之后直接重定向刷新当前页即可
        return "redirect:/admin/";
    }

    /**
     * @Description 根据用户id查询用户, 并去往用户修改页面回显
     * @Param adminId
     * @Return org.springframework.web.servlet.ModelAndView
     * @Author electroNic
     * @Date 2022/11/29 3:31
     */
    @RequestMapping("/edit/{adminId}")
    public ModelAndView toEditPage(@PathVariable Long adminId) {
        Admin admin = adminService.getById(adminId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("admin", admin);
        mv.setViewName("admin/edit");
        return mv;
    }

    /**
     * @Description 执行修改用户操作
     * @Param none
     * @Return org.springframework.web.servlet.ModelAndView
     * @Author electroNic
     * @Date 2022/11/29 3:36
     */
    @RequestMapping("/update")
    public ModelAndView update(Admin admin) {
        adminService.update(admin);
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "修改用户成功");
        mv.setViewName(SUCCESS_PAGE);
        return mv;
    }

    @RequestMapping("/uploadShow/{id}")
    public ModelAndView toUploadPage(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("id", id);
        mv.setViewName("admin/upload");
        return mv;
    }

    @RequestMapping("/upload/{id}")
    public ModelAndView doUpload(@PathVariable("id") Long id,
                                 @RequestParam("file") MultipartFile file) {
        ModelAndView mv = new ModelAndView();
        Result result = adminService.doUpload(id, file);
        mv.addObject("message", "头像设置成功");
        mv.setViewName("common/success");
        return mv;
    }

    // 去分配角色的界面
    @RequestMapping("/assignShow/{adminId}")
    public ModelAndView toAssignShowPage(@PathVariable("adminId") Long adminId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("adminId", adminId);
        // 得到已分配和未分配的权限集合
        Map<String, List<Role>> roleList = roleService.selectRolesByAdminId(adminId);
        mv.addAllObjects(roleList);
        mv.setViewName("admin/assignShow");
        return mv;
    }

    // 分配角色操作
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds) {
        roleService.assignRole(adminId, roleIds);
        return "common/success";
    }
}

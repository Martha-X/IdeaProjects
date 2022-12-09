package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.Admin;
import com.electronic.entity.HouseBroker;
import com.electronic.service.AdminService;
import com.electronic.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {
    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;

    // 去添加经纪人页面
    @RequestMapping("/create")
    public ModelAndView toCreatePage(@RequestParam("houseId") Long houseId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("houseId", houseId);
        List<Admin> adminList = adminService.getAll();
        mv.addObject("adminList", adminList);
        mv.setViewName("houseBroker/create");
        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView save(HouseBroker houseBroker) {
        ModelAndView mv = new ModelAndView();
        // 根据经纪人id查询经纪人信息(头像和名字)
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        mv.addObject("message", "添加经纪人成功");
        mv.setViewName("common/success");
        return mv;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView toEditPage(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        HouseBroker houseBroker = houseBrokerService.getById(id);
        List<Admin> adminList = adminService.getAll();
        mv.addObject("houseBroker", houseBroker);
        mv.addObject("adminList", adminList);
        mv.setViewName("houseBroker/edit");
        return mv;
    }

    @RequestMapping("/update")
    public ModelAndView update(HouseBroker houseBroker) {
        ModelAndView mv = new ModelAndView();
        // 根据经纪人id查询经纪人信息(头像和名字)
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        mv.addObject("message", "修改经纪人成功");
        mv.setViewName("common/success");
        return mv;
    }

    @RequestMapping("/delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("brokerId") Long brokerId) {
        houseBrokerService.delete(brokerId);
        return "redirect:/house/" + houseId;
    }
}

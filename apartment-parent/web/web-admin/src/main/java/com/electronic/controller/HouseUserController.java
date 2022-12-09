package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.entity.HouseUser;
import com.electronic.service.AdminService;
import com.electronic.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/houseUser")
public class HouseUserController {
    @Reference
    private AdminService adminService;
    @Reference
    private HouseUserService houseUserService;

    // 去添加房东页面
    @RequestMapping("/create")
    public ModelAndView toCreatePage(@RequestParam("houseId") Long houseId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("houseId", houseId);
        mv.setViewName("houseUser/create");
        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView save(HouseUser houseUser) {
        ModelAndView mv = new ModelAndView();
        houseUserService.insert(houseUser);
        mv.addObject("message", "添加房东成功");
        mv.setViewName("common/success");
        return mv;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView toEditPage(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        HouseUser houseUser = houseUserService.getById(id);
        mv.addObject("houseUser", houseUser);
        mv.setViewName("houseUser/edit");
        return mv;
    }

    @RequestMapping("/update")
    public ModelAndView update(HouseUser houseUser) {
        ModelAndView mv = new ModelAndView();
        houseUserService.update(houseUser);
        mv.addObject("message", "修改房东成功");
        mv.setViewName("common/success");
        return mv;
    }

    @RequestMapping("/delete/{houseId}/{houseUserId}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("houseUserId") Long houseUserId) {
        houseUserService.delete(houseUserId);
        return "redirect:/house/" + houseId;
    }
}

package com.electronic.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.electronic.result.Result;
import com.electronic.service.HouseImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    @Reference
    private HouseImageService houseImageService;

    // 去上传图片的页面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public ModelAndView toUploadPage(@PathVariable("houseId") Long houseId,
                                     @PathVariable("type") Integer type) {
        // 将房源id和图片类型放到request域对象中
        ModelAndView mv = new ModelAndView();
        mv.addObject("houseId", houseId);
        mv.addObject("type", type);
        mv.setViewName("house/upload");
        return mv;
    }

    // 上传房源或者房产图片
    @ResponseBody
    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId,
                         @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files) {
        return houseImageService.doUpload(houseId, type, files);
    }

    // 删除房源或者房产图片
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("id") Long id) {
        // 逻辑删除数据库中数据,七牛云的未删,也可以考虑删除
        houseImageService.delete(id);
        return "redirect:/house/" + houseId;
    }
}

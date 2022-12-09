package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.HouseImage;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.HouseImageMapper;
import com.electronic.result.Result;
import com.electronic.service.HouseImageService;
import com.electronic.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageMapper houseImageMapper;

    @Override
    protected BaseMapper<HouseImage> getEntityMapper() {
        return houseImageMapper;
    }

    @Override
    public List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId, Integer type) {
        return houseImageMapper.getHouseImagesByHouseIdAndType(houseId, type);
    }

    @Override
    public Result doUpload(Long houseId, Integer type, MultipartFile[] files) {
        try {
            // 判空
            if (files != null && files.length > 0) {
                // 遍历每一个文件
                for (MultipartFile file : files) {
                    // 获取每一个文件的字节数组
                    byte[] bytes = file.getBytes();
                    // 获取图片名称,若名称相同会导致相同名称图片无法上传或者被覆盖,所以这里使用UUID作为图片名称
//                    String filename = file.getOriginalFilename();
                    // 通过UUID随机生成一个字符串作为上传到七牛云的图片名称
                    String filename = UUID.randomUUID().toString();
                    // 通过qiniu工具类上传图片到七牛云
                    QiniuUtils.upload2Qiniu(bytes, filename);
                    // 图片上传到云对象之后,往数据库中插入数据
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setImageName(filename);
                    houseImage.setType(type);
                    // 设置图片路径:路径格式为http://七牛云域名/文件名称
                    houseImage.setImageUrl("http://rm8ad400x.hn-bkt.clouddn.com/" + filename);
                    houseImageMapper.insert(houseImage);
                }
            } else {
                return Result.ok().message("文件为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail().message("文件上传失败");
        }
        return Result.ok().message("上传文件成功");
    }
}

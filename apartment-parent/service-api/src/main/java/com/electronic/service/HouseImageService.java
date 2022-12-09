package com.electronic.service;

import com.electronic.entity.HouseImage;
import com.electronic.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {
    // 根据房源id和类型查询房源或房产图片
    List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId, Integer type);

    // 处理文件上传
    Result doUpload(Long houseId, Integer type, MultipartFile[] files);
}

package com.electronic.mapper;

import com.electronic.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageMapper extends BaseMapper<HouseImage> {
    // 根据房源id和类型查询房源或房产图片
    List<HouseImage> getHouseImagesByHouseIdAndType(@Param("houseId") Long houseId, @Param("type") Integer type);
}

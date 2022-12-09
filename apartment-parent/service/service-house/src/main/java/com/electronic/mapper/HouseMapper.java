package com.electronic.mapper;

import com.electronic.entity.House;
import com.electronic.vo.HouseQueryVo;
import com.electronic.vo.HouseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMapper extends BaseMapper<House> {
    void publish(@Param("id") Long id, @Param("status") Integer status);

    List<HouseVo> selectPageList(HouseQueryVo houseQueryVo);
}

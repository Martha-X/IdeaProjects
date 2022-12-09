package com.electronic.service;

import com.electronic.entity.House;
import com.electronic.result.Result;
import com.electronic.vo.HouseQueryVo;

public interface HouseService extends BaseService<House>{
    void publish(Long id, Integer status);

    Result selectPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}

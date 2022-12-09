package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.House;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.DictMapper;
import com.electronic.mapper.HouseMapper;
import com.electronic.result.Result;
import com.electronic.service.HouseService;
import com.electronic.vo.HouseQueryVo;
import com.electronic.vo.HouseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private DictMapper dictMapper;

    @Override
    protected BaseMapper<House> getEntityMapper() {
        return this.houseMapper;
    }

    @Override
    public void publish(Long id, Integer status) {
        houseMapper.publish(id, status);
    }

    @Override
    public Result selectPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        PageHelper.startPage(pageNum, pageSize);
        List<HouseVo> houseVos = houseMapper.selectPageList(houseQueryVo);
        for (HouseVo houseVo : houseVos) {
            houseVo.setHouseTypeName(dictMapper.getNameById(houseVo.getHouseTypeId()));
            houseVo.setFloorName(dictMapper.getNameById(houseVo.getFloorId()));
            houseVo.setDirectionName(dictMapper.getNameById(houseVo.getDirectionId()));
        }
        PageInfo<HouseVo> page = new PageInfo<>(houseVos);
        return Result.ok(page);
    }

    @Override
    public House getById(Serializable id) {
        House house = houseMapper.getById(id);
        // 获取户型
        house.setHouseTypeName(dictMapper.getNameById(house.getHouseTypeId()));
        // 获取楼层
        house.setFloorName(dictMapper.getNameById(house.getFloorId()));
        // 获取朝向
        house.setDirectionName(dictMapper.getNameById(house.getDirectionId()));
        // 获取建筑结构
        house.setBuildStructureName(dictMapper.getNameById(house.getBuildStructureId()));
        // 获取装修情况
        house.setDecorationName(dictMapper.getNameById(house.getDecorationId()));
        // 获取房屋用途
        house.setHouseUseName(dictMapper.getNameById(house.getHouseUseId()));
        return house;
    }
}

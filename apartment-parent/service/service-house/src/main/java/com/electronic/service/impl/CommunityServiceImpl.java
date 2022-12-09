package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.Community;
import com.electronic.mapper.BaseMapper;
import com.electronic.mapper.CommunityMapper;
import com.electronic.mapper.DictMapper;
import com.electronic.service.CommunityService;
import com.electronic.utils.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private DictMapper dictMapper;

    @Override
    protected BaseMapper<Community> getEntityMapper() {
        return this.communityMapper;
    }

    // 重写分页查询方法,目的是为了给小区中的areaName 和 plateName赋值
    @Override
    public PageInfo<Community> selectAllAsPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum, pageSize);
        List<Community> communities = communityMapper.selectAllAsPage(filters);
        for (Community community : communities) {
            // 根据区域id获取区域名称
            community.setAreaName(dictMapper.getNameById(community.getAreaId()));
            // 根据板块id获取版本名称
            community.setPlateName(dictMapper.getNameById(community.getPlateId()));
        }
        return new PageInfo<>(communities);
    }

    @Override
    public List<Community> getAllCommunities() {
        return communityMapper.selectAllAsPage(null);
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityMapper.getById(id);
        // 根据区域id获取区域名称
        community.setAreaName(dictMapper.getNameById(community.getAreaId()));
        // 根据板块id获取版本名称
        community.setPlateName(dictMapper.getNameById(community.getPlateId()));
        return community;
    }
}

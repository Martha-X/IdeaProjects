package com.electronic.service;

import com.electronic.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community>{
    List<Community> getAllCommunities();
}

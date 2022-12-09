package com.electronic.service;

import com.electronic.entity.Admin;
import com.electronic.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService extends BaseService<Admin> {
    List<Admin> getAll();

    Result doUpload(Long id, MultipartFile file);

    Admin getAdminByUserName(String username);
}

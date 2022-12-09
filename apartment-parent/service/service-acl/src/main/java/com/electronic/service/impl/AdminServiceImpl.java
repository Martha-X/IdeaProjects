package com.electronic.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.electronic.entity.Admin;
import com.electronic.mapper.AdminMapper;
import com.electronic.mapper.BaseMapper;
import com.electronic.result.Result;
import com.electronic.service.AdminService;
import com.electronic.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected BaseMapper<Admin> getEntityMapper() {
        return this.adminMapper;
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.getAll();
    }

    @Override
    public Result doUpload(Long id, MultipartFile file) {
        try {
            // 查询当前用户,给用户设置头像地址
            Admin admin = adminMapper.getById(id);
            // 判空
            if (file != null && !file.isEmpty()) {
                // 获取字节数组
                byte[] bytes = file.getBytes();
                // 随机生成文件名称
                String avatar = UUID.randomUUID().toString();
                // 上传到云端
                QiniuUtils.upload2Qiniu(bytes, avatar);
                // 保存数据到数据库
                admin.setHeadUrl("http://rm8ad400x.hn-bkt.clouddn.com/" + avatar);
                adminMapper.update(admin);
            } else
                return Result.ok().message("文件为空");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail().message("头像设置失败");
        }
        return Result.ok().message("头像设置成功");
    }

    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.getAdminByUserName(username);
    }
}

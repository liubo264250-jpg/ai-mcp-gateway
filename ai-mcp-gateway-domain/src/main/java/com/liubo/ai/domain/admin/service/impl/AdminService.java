package com.liubo.ai.domain.admin.service.impl;

import com.liubo.ai.domain.admin.adapter.repository.IAdminRepository;
import com.liubo.ai.domain.admin.model.entity.GatewayConfigEntity;
import com.liubo.ai.domain.admin.service.IAdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 20:18
 */
@Service
public class AdminService implements IAdminService {
    @Resource
    private IAdminRepository adminRepository;

    @Override
    public List<GatewayConfigEntity> queryGatewayConfigList() {
        return adminRepository.queryGatewayConfigList();
    }
}

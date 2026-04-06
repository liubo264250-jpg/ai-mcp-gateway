package com.liubo.ai.cases.admin.manage;

import com.liubo.ai.cases.admin.IAdminManageService;
import com.liubo.ai.domain.admin.model.entity.GatewayConfigEntity;
import com.liubo.ai.domain.admin.service.IAdminService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 20:33
 */
@Slf4j
@Service
public class AdminManageService  implements IAdminManageService {
    @Resource
    private IAdminService adminService;

    @Override
    public List<GatewayConfigEntity> queryGatewayConfigList() {
        return adminService.queryGatewayConfigList();
    }
}

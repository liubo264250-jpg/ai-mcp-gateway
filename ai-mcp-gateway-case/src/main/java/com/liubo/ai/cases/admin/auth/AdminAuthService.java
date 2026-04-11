package com.liubo.ai.cases.admin.auth;

import com.liubo.ai.cases.admin.IAdminAuthService;
import com.liubo.ai.domain.auth.model.entity.RegisterCommandEntity;
import com.liubo.ai.domain.auth.service.IAuthRegisterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 68
 * 2026/4/6 20:31
 */
@Slf4j
@Service
public class AdminAuthService implements IAdminAuthService{
    @Resource
    private IAuthRegisterService authRegisterService;

    @Override
    public void saveGatewayAuth(RegisterCommandEntity commandEntity) {
        authRegisterService.register(commandEntity);
    }

    @Override
    public void deleteGatewayAuth(String gatewayId) {
        authRegisterService.deleteGatewayAuth(gatewayId);
    }
}

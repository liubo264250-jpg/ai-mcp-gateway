package com.liubo.ai.cases.admin;

import com.liubo.ai.domain.auth.model.entity.RegisterCommandEntity;

/**
 * @author 68
 * 2026/4/6 20:30
 */
public interface IAdminAuthService {
    void saveGatewayAuth(RegisterCommandEntity commandEntity);
    void deleteGatewayAuth(String gatewayId);
}

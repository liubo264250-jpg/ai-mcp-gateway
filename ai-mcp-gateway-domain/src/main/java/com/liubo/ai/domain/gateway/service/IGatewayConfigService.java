package com.liubo.ai.domain.gateway.service;

import com.liubo.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;

/**
 * @author 68
 * 2026/4/6 17:33
 */
public interface IGatewayConfigService {
    void saveGatewayConfig(GatewayConfigCommandEntity commandEntity);

    void updateGatewayAuthStatus(GatewayConfigCommandEntity commandEntity);
}

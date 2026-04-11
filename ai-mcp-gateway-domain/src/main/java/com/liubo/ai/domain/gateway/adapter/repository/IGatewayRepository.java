package com.liubo.ai.domain.gateway.adapter.repository;

import com.liubo.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import com.liubo.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;

/**
 * @author 68
 * 2026/4/6 17:31
 */
public interface IGatewayRepository {
    void saveGatewayConfig(GatewayConfigCommandEntity commandEntity);
    void updateGatewayAuthStatus(GatewayConfigCommandEntity commandEntity);
    void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity);
    void updateGatewayToolProtocol(GatewayToolConfigCommandEntity commandEntity);

    void deleteGatewayToolConfig(Long toolId);
}

package com.liubo.ai.domain.gateway.service;

import com.liubo.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;

/**
 * @author 68
 * 2026/4/6 17:33
 */
public interface IGatewayToolConfigService {
    void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity);

    void updateGatewayToolProtocol(GatewayToolConfigCommandEntity commandEntity);

    void deleteGatewayToolConfig(Long toolId);
}

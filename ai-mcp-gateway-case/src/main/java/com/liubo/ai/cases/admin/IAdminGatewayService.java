package com.liubo.ai.cases.admin;

import com.liubo.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import com.liubo.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;

/**
 * @author 68
 * 2026/4/6 20:30
 */
public interface IAdminGatewayService {
    void saveGatewayConfig(GatewayConfigCommandEntity commandEntity);

    void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity);

    void deleteGatewayToolConfig(Long toolId);
}

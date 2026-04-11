package com.liubo.ai.cases.admin.gateway;

import com.liubo.ai.cases.admin.IAdminGatewayService;
import com.liubo.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import com.liubo.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;
import com.liubo.ai.domain.gateway.service.IGatewayConfigService;
import com.liubo.ai.domain.gateway.service.IGatewayToolConfigService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 68
 * 2026/4/6 20:32
 */
@Slf4j
@Service
public class AdminGatewayService implements IAdminGatewayService {
    @Resource
    private IGatewayConfigService gatewayConfigService;

    @Resource
    private IGatewayToolConfigService gatewayToolConfigService;

    @Override
    public void saveGatewayConfig(GatewayConfigCommandEntity commandEntity) {
        gatewayConfigService.saveGatewayConfig(commandEntity);
    }

    @Override
    public void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity) {
        gatewayToolConfigService.saveGatewayToolConfig(commandEntity);
    }
    @Override
    public void deleteGatewayToolConfig(Long toolId) {
        gatewayToolConfigService.deleteGatewayToolConfig(toolId);
    }
}

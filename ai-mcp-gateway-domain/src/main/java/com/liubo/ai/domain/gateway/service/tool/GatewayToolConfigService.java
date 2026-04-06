package com.liubo.ai.domain.gateway.service.tool;

import com.liubo.ai.domain.gateway.adapter.repository.IGatewayRepository;
import com.liubo.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;
import com.liubo.ai.domain.gateway.service.IGatewayToolConfigService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 68
 * 2026/4/6 17:34
 */
@Slf4j
@Service
public class GatewayToolConfigService implements IGatewayToolConfigService {
    @Resource
    private IGatewayRepository repository;

    @Override
    public void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity) {
        repository.saveGatewayToolConfig(commandEntity);
    }

    @Override
    public void updateGatewayToolProtocol(GatewayToolConfigCommandEntity commandEntity) {
        repository.updateGatewayToolProtocol(commandEntity);
    }
}

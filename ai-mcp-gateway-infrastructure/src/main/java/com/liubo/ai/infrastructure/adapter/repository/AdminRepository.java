package com.liubo.ai.infrastructure.adapter.repository;

import com.liubo.ai.domain.admin.adapter.repository.IAdminRepository;
import com.liubo.ai.domain.admin.model.entity.GatewayConfigEntity;
import com.liubo.ai.infrastructure.dao.po.McpGateway;
import com.liubo.ai.infrastructure.dao.service.McpGatewayService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 68
 * 2026/4/6 20:26
 */
@Slf4j
@Repository
public class AdminRepository implements IAdminRepository {

    @Resource
    private McpGatewayService mcpGatewayService;

    @Override
    public List<GatewayConfigEntity> queryGatewayConfigList() {
        List<McpGateway> mcpGatewayPOS = mcpGatewayService.list();
        return mcpGatewayPOS.stream().map(po -> GatewayConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .gatewayName(po.getGatewayName())
                .gatewayDesc(po.getGatewayDesc())
                .version(po.getVersion())
                .auth(po.getAuth())
                .status(po.getStatus())
                .build()).collect(Collectors.toList());
    }
}

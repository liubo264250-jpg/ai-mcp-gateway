package com.liubo.ai.infrastructure.adapter.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liubo.ai.domain.session.adapter.repository.ISessionRepository;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import com.liubo.ai.infrastructure.dao.po.McpGateway;
import com.liubo.ai.infrastructure.dao.po.McpProtocolRegistry;
import com.liubo.ai.infrastructure.dao.service.McpGatewayService;
import com.liubo.ai.infrastructure.dao.service.McpProtocolRegistryService;
import com.liubo.ai.types.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 68
 * 2026/3/29 22:51
 */
@Repository
public class SessionRepository implements ISessionRepository {

    @Autowired
    private McpGatewayService mcpGatewayService;

    @Autowired
    private McpProtocolRegistryService mcpProtocolRegistryService;

    @Override
    public McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId) {
        if (StrUtil.isBlank(gatewayId)) return null;
        McpGateway mcpGateway = mcpGatewayService.getOne(Wrappers.<McpGateway>lambdaQuery()
                .eq(McpGateway::getGatewayId, gatewayId)
                .eq(McpGateway::getStatus, CommonConstant.ENABLE));
        if (mcpGateway == null) return null;

        McpProtocolRegistry mcpProtocolRegistry = mcpProtocolRegistryService.getOne(Wrappers.<McpProtocolRegistry>lambdaQuery()
                .eq(McpProtocolRegistry::getGatewayId, gatewayId)
                .eq(McpProtocolRegistry::getStatus, CommonConstant.ENABLE));
        if (null == mcpProtocolRegistry) return null;

        return McpGatewayConfigVO.builder()
                .gatewayId(mcpGateway.getGatewayId())
                .gatewayName(mcpGateway.getGatewayName())
                .toolId(mcpProtocolRegistry.getToolId())
                .toolName(mcpProtocolRegistry.getToolName())
                .toolDescription(mcpProtocolRegistry.getToolDescription())
                .toolVersion(mcpProtocolRegistry.getToolVersion())
                .build();
    }
}

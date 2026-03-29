package com.liubo.ai.domain.session.adapter.repository;

import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;

/**
 * @author 68
 * 2026/3/29 22:50
 */
public interface ISessionRepository {
    McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId);
}

package com.liubo.ai.domain.session.adapter.repository;

import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpToolConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpToolProtocolConfigVO;

import java.util.List;

/**
 * @author 68
 * 2026/3/29 22:50
 */
public interface ISessionRepository {
    McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId);
    List<McpToolConfigVO> queryMcpGatewayToolConfigListByGatewayId(String gatewayId);
    McpToolProtocolConfigVO queryMcpGatewayProtocolConfig(String gatewayId, String toolName);
}

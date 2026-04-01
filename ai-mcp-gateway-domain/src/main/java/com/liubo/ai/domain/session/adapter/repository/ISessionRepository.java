package com.liubo.ai.domain.session.adapter.repository;

import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayProtocolConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayToolConfigVO;

import java.util.List;

/**
 * @author 68
 * 2026/3/29 22:50
 */
public interface ISessionRepository {
    McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId);
    List<McpGatewayToolConfigVO> queryMcpGatewayToolConfigListByGatewayId(String gatewayId);
    McpGatewayProtocolConfigVO queryMcpGatewayProtocolConfig(String gatewayId);
}

package com.liubo.ai.domain.session.model.valobj.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/4/3 23:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McpToolConfigVO {
    /**
     * 所属网关ID
     */
    private String gatewayId;

    /**
     * 所属工具ID
     */
    private Long toolId;

    /**
     * MCP工具名称（如：JavaSDKMCPClient_getCompanyEmployee）
     */
    private String toolName;

    /**
     * 工具描述
     */
    private String toolDescription;

    /**
     * 工具版本
     */
    private String toolVersion;

    /**
     * 协议配置
     */
    private McpToolProtocolConfigVO mcpToolProtocolConfigVO;
}

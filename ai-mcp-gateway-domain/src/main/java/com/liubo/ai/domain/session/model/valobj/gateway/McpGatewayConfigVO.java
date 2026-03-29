package com.liubo.ai.domain.session.model.valobj.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/3/29 22:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McpGatewayConfigVO {
    /**
     * 网关唯一标识
     */
    private String gatewayId;

    /**
     * 网关名称
     */
    private String gatewayName;

    /**
     * 工具ID
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
}

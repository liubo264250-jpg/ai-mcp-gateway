package com.liubo.ai.domain.gateway.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/4/6 17:32
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayToolConfigVO {
    /**
     * 所属网关ID
     */
    private String gatewayId;
    /**
     * 工具ID
     */
    private Long toolId;
    /**
     * MCP工具名称（如：JavaSDKMCPClient_getCompanyEmployee）
     */
    private String toolName;
    /**
     * 工具类型：function/resource
     */
    private String toolType;
    /**
     * 工具描述
     */
    private String toolDescription;
    /**
     * 工具版本
     */
    private String toolVersion;
    /**
     * 协议ID
     */
    private Long protocolId;
    /**
     * 协议类型；协议类型；http、dubbo、rabbitmq
     */
    private String protocolType;
}

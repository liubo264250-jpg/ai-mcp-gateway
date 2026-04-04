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
     * 网关ID
     */
    private String gatewayId;

    /**
     * 网关名称
     */
    private String gatewayName;

    /**
     * 网关描述
     */
    private String gatewayDesc;

    /**
     * 协议版本
     */
    private String version;

}

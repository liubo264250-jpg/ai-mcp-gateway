package com.liubo.ai.domain.admin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/4/6 20:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GatewayConfigEntity {
    private String gatewayId;
    private String gatewayName;
    private String gatewayDesc;
    private String version;
    private Integer auth;
    private Integer status;
}

package com.liubo.ai.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 68
 * 2026/4/6 19:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GatewayConfigDTO implements Serializable {
    private String gatewayId;
    private String gatewayName;
    private String gatewayDesc;
    private String version;
    private Integer auth;
    private Integer status;
}

package com.liubo.ai.domain.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/4/5 20:04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LicenseCommandEntity {
    /**
     * 网关ID
     */
    private String gatewayId;

    /**
     * API密钥
     */
    private String apiKey;
}

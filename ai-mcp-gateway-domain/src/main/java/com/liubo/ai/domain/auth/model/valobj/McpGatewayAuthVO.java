package com.liubo.ai.domain.auth.model.valobj;

import com.liubo.ai.types.enums.AuthStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 68
 * 2026/4/5 20:15
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class McpGatewayAuthVO {    /**
 * 网关ID
 */
private String gatewayId;
    /**
     * API密钥
     */
    private String apiKey;
    /**
     * 速率限制（次/小时）
     */
    private Integer rateLimit;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 状态：0-禁用，1-启用
     */
    private AuthStatusEnum.AuthConfig status;
}

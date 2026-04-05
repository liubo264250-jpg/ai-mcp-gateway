package com.liubo.ai.domain.auth.adapter.repository;

import com.liubo.ai.domain.auth.model.entity.LicenseCommandEntity;
import com.liubo.ai.domain.auth.model.valobj.McpGatewayAuthVO;
import com.liubo.ai.types.enums.AuthStatusEnum;

/**
 * @author 68
 * 2026/4/5 20:14
 */
public interface IAuthRepository {
    McpGatewayAuthVO queryGatewayAuthInfo(LicenseCommandEntity commandEntity);
    void insert(McpGatewayAuthVO  mcpGatewayAuthVO);

    AuthStatusEnum.GatewayConfig queryGatewayAuthStatus(String gatewayId);
}

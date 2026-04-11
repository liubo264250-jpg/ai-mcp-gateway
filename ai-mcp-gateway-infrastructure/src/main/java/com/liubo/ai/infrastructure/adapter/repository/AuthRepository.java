package com.liubo.ai.infrastructure.adapter.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liubo.ai.domain.auth.adapter.repository.IAuthRepository;
import com.liubo.ai.domain.auth.model.entity.LicenseCommandEntity;
import com.liubo.ai.domain.auth.model.valobj.McpGatewayAuthVO;
import com.liubo.ai.infrastructure.dao.po.McpGateway;
import com.liubo.ai.infrastructure.dao.po.McpGatewayAuth;
import com.liubo.ai.infrastructure.dao.service.McpGatewayAuthService;
import com.liubo.ai.infrastructure.dao.service.McpGatewayService;
import com.liubo.ai.types.constant.CommonConstant;
import com.liubo.ai.types.enums.AuthStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 68
 * 2026/4/5 20:23
 */
@Repository
public class AuthRepository implements IAuthRepository{

    @Autowired
    private McpGatewayService mcpGatewayService;

    @Autowired
    private McpGatewayAuthService  mcpGatewayAuthService;

    @Override
    public McpGatewayAuthVO queryGatewayAuthInfo(LicenseCommandEntity commandEntity) {
        if (null == commandEntity || StrUtil.isBlank(commandEntity.getGatewayId())
                || StrUtil.isBlank(commandEntity.getApiKey())){
            return null;
        }
        McpGatewayAuth mcpGatewayAuth = mcpGatewayAuthService.getOne(Wrappers.<McpGatewayAuth>lambdaQuery().eq(McpGatewayAuth::getGatewayId, commandEntity.getGatewayId())
                .eq(McpGatewayAuth::getApiKey, commandEntity.getApiKey()));
        if (null == mcpGatewayAuth) return null;
        return McpGatewayAuthVO.builder()
                .gatewayId(mcpGatewayAuth.getGatewayId())
                .apiKey(mcpGatewayAuth.getApiKey())
                .rateLimit(mcpGatewayAuth.getRateLimit())
                .expireTime(mcpGatewayAuth.getExpireTime())
                .status(AuthStatusEnum.AuthConfig.get(mcpGatewayAuth.getStatus()))
                .build();
    }

    @Override
    public void insert(McpGatewayAuthVO mcpGatewayAuthVO) {
        McpGatewayAuth mcpGatewayAuth = new McpGatewayAuth();
        mcpGatewayAuth.setGatewayId(mcpGatewayAuthVO.getGatewayId());
        mcpGatewayAuth.setApiKey(mcpGatewayAuthVO.getApiKey());
        mcpGatewayAuth.setRateLimit(mcpGatewayAuthVO.getRateLimit());
        mcpGatewayAuth.setExpireTime(mcpGatewayAuthVO.getExpireTime());
        mcpGatewayAuth.setStatus(mcpGatewayAuthVO.getStatus().getCode());
        mcpGatewayAuthService.save(mcpGatewayAuth);
    }

    @Override
    public AuthStatusEnum.GatewayConfig queryGatewayAuthStatus(String gatewayId) {

        McpGateway mcpGateway = mcpGatewayService.getOne(Wrappers.<McpGateway>lambdaQuery()
                .eq(McpGateway::getGatewayId,gatewayId)
                .eq(McpGateway::getStatus, CommonConstant.ENABLE));
        return AuthStatusEnum.GatewayConfig.get(mcpGateway.getAuth());
    }

    @Override
    public void deleteGatewayAuth(String gatewayId) {
        mcpGatewayAuthService.remove(Wrappers.<McpGatewayAuth>lambdaQuery().eq(McpGatewayAuth::getGatewayId, gatewayId));
    }
}

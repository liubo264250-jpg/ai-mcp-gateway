package com.liubo.ai.domain.auth.service.register;

import cn.hutool.core.util.RandomUtil;
import com.liubo.ai.domain.auth.adapter.repository.IAuthRepository;
import com.liubo.ai.domain.auth.model.entity.RegisterCommandEntity;
import com.liubo.ai.domain.auth.model.valobj.McpGatewayAuthVO;
import com.liubo.ai.domain.auth.service.IAuthRegisterService;
import com.liubo.ai.types.enums.AuthStatusEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 68
 * 2026/4/5 20:07
 */
@Service
@Slf4j
public class AuthRegisterService implements IAuthRegisterService{
    @Resource
    private IAuthRepository repository;

    @Override
    public String register(RegisterCommandEntity commandEntity) {
        // 1. 生成 API Key | gw 网关缩写，方便区分
        String apiKey = "gw-" + RandomUtil.randomString(48);

        // 2. 构建聚合对象
        McpGatewayAuthVO mcpGatewayAuthVO = McpGatewayAuthVO.builder()
                .gatewayId(commandEntity.getGatewayId())
                .apiKey(apiKey)
                .rateLimit(commandEntity.getRateLimit())
                .expireTime(commandEntity.getExpireTime())
                .status(AuthStatusEnum.AuthConfig.ENABLE)
                .build();

        // 3. 保存数据
        repository.insert(mcpGatewayAuthVO);

        // 4. 返回结果
        return apiKey;
    }

    @Override
    public void deleteGatewayAuth(String gatewayId) {
        repository.deleteGatewayAuth(gatewayId);
    }

}

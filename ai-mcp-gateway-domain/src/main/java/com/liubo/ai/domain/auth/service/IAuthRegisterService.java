package com.liubo.ai.domain.auth.service;

import com.liubo.ai.domain.auth.model.entity.RegisterCommandEntity;

/**
 * @author 68
 * 2026/4/5 20:07
 */
public interface IAuthRegisterService {

    String register(RegisterCommandEntity commandEntity);

    void deleteGatewayAuth(String gatewayId);
}

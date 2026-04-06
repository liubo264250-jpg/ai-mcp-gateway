package com.liubo.ai.domain.admin.service;

import com.liubo.ai.domain.admin.model.entity.GatewayConfigEntity;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 20:18
 */
public interface IAdminService {
    List<GatewayConfigEntity> queryGatewayConfigList();
}

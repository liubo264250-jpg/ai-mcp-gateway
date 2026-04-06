package com.liubo.ai.domain.admin.adapter.repository;

import com.liubo.ai.domain.admin.model.entity.GatewayConfigEntity;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 20:20
 */
public interface IAdminRepository {
    List<GatewayConfigEntity> queryGatewayConfigList();
}

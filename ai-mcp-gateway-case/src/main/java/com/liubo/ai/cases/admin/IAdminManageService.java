package com.liubo.ai.cases.admin;

import com.liubo.ai.domain.admin.model.entity.GatewayConfigEntity;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 20:31
 */
public interface IAdminManageService {
    List<GatewayConfigEntity> queryGatewayConfigList();
}
